include "app://player.ms"

// Simple artifactial inteligence
class PlayerAI : Player
{
    // constructor
    function this(table, paddle)
    {
        super(table, paddle);
        // joint is used to manipulate with player paddle
        this.joint = this._createJoint();
        // hit time (delay for repeated attack)
        this.hitTime = 0;
    }

    // @region public functions
    // resets player instance
    function reset(clearScore = true)
    {
        super.reset(clearScore);
        this.paddle.setPosition(System.width / 2, System.height / 4);
    }

    // called by Table object (onProcess)
    function handleProcess()
    {
        // get position of my paddle
        var (x, y) = this.paddle.getPosition();
        // get position of puck
        var (px, py) = this.table.puck.getPosition();
        // delay & defense after contact
        if (System.tick - this.hitTime < 700) {
            this._defense(x, y, px, py);
            return;
        }
        // otherwise make a desiciton
        this._makeDecision(x, y, px, py);
    }

    // called by Table when AI player hits a puck
    function hit()
    {
        this.hitTime = System.tick;
    }
    // @endregion

    // @region private functions
    function _makeDecision(x, y, px, py)
    {
        // attack when puck is in our corner
        var puckInCorner = px < System.width / 5 || px > 4*System.width / 5;
        if (puckInCorner && py < 2 * this.puckRadius ) {
            return ;
        }

        // move to puck's position and hit puck to the second half of table
        if (py < ( 9 * System.height / 20))
            return this._moveTo(x, y, px, py - this.puckRadius / 4);

        return this._defense(x, y, px, py);
    }

    // simple defence method
    function _defense(x, y, px, py)
    {
        if (py < y && Math.abs(System.width / 2 - px) > System.width / 5)
            return this._moveTo(x, y, px, py - this.puckRadius);
    
        this._moveTo(x, y, System.width / 4 + System.width / 2 * (px / (1.0 * System.width)) , System.height / 6);
        return true;
    }

    // calculates movement for AI paddle
    function _moveTo(ox, oy, px, py)
    {
        // be random
        var speed = Integer.min(640, System.width) / (40.0 + rand(20));
        // calculate deltas
        const dx = px - ox;
        const dy = py - oy;
        // calculate distance between puck and paddle position (we use Pythagorean theorem)
        const distance = Math.sqrt(dx * dx + dy * dy);
        // if total distance is greater than the distance, of which we can move in one step calculate new x and y coordinates somewhere between current puck and paddle position.
        if (distance > speed) {
            // x = current padle x position + equally part of speed on x axis
            px = ox + speed / distance * dx;
            py = oy + speed / distance * dy;
        }
        // move paddle to the new position
        this.joint.setTarget(this.table.x2box2d(px), this.table.y2box2d(py));
        return true;
    }

    // "private" function that creates mouse joint
    function _createJoint()
    {
        // just "shortcut"
        const table = this.table;
        // calculate x, y
        const x = System.width / 2;
        const y = System.height / 4;
        // prepare box2d mouse joint definition
        var mouseJointDef = {
            maxForce : 100000,
            frequencyHz : 10000,
            dampingRatio : 1.0,
            targetX : table.x2box2d(x),
            targetY: table.y2box2d(y)
        };
        // move paddle to target position
        this.paddle.setTransform(x, y);
        // create mouse joint
        return this.table.createMouseJoint(table._ground, this.paddle, mouseJointDef, true);
    }
    // @endregion
}
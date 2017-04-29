include "app://player.ms"

// Human player class
class PlayerHuman : Player
{
    // constructor
    function this(table, paddle)
    {
        super(table, paddle);
        // create joint
        this.joint = null;
    }

    // @region public functions
    // resets object (paddle, score, etc)
    function reset(clearScore = true)
    {
        super.reset(clearScore);
        if (this.joint != null) {
            this.table.world.destroyJoint(this.joint);
            this.joint = null;
        }
        this.paddle.setLinearDamping(50);
        this.paddle.setAngularDamping(50);
        this.paddle.setPosition(System.width / 2, 3 * System.height / 4);
    }

    // called by Table when touch down occured
    function handlePressed(x, y)
    {
        // check player's side
        if (y < System.height / 2)
            y = System.height / 2;
        // just simple helper
        const table = this.table;

        // mouse joint definition
        var mouseJointDef = {
            maxForce : 10000,
            frequencyHz : 1000,
            dampingRatio : 0.0,
            targetX : table.x2box2d(x), // specified in box2d coords
            targetY : table.y2box2d(y)  // specified in box2d coords
        };
        // move paddle to touched place
        this.paddle.setTransform(x, y);
        // create mouse joint
        if (this.joint)
            this.table.destroyJoint(this.joint);
        this.joint = table.createMouseJoint(table.ground, this.paddle, mouseJointDef, true);
    }

    // called by Table when touch drag occured
    function handleDragged(x, y)
    {
        // limit player's side
        if (y < System.height / 2 + this.puckRadius)
            y = System.height / 2 + this.puckRadius;

        // affect mouse joint
        if (this.joint != null)
            this.joint.setTarget(this.table.x2box2d(x), this.table.y2box2d(y));
    }

    // called by Table when touch up occured
    function handleReleased(x, y)
    {
        // check player's side
        if (y < System.height / 2)
            y = System.height / 2;
        // affect mouse joint position & destroy it
        if (this.joint != null) {
            this.joint.setTarget(this.table.x2box2d(x), this.table.y2box2d(y));
            this.table.destroyJoint(this.joint);
            this.joint = null;
        }
    }
    // @endregion
}
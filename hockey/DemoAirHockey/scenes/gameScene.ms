include "app://scenes/menuLayer.ms"
include "app://scenes/winnerLayer.ms"

class GameScene : PhysicsScene
{
    var gameStarted = false;

    function init()
    {
        // create physics world
        this._world = new b2World(0.0, 0.0, true, true);
        // world callback
        this.onBeginContact = function(sender, contact) { this super._beginContact(contact); }
        this.onEndContact = function(sender, contact) { this super._endContact(contact); }

        // calculate some values
        this.goalsWidth = 15 * System.width / 32;
        this.puckRadius = res.images.puck.width / 2;
        this._goal = false;
        this._win = false;

        this._createBarriers();
        this._createGoals();
        this._puck = this._createPuck();

        // create ground (necessary for mouse joints)
        this._ground = this.addPolygonBody(null, #static, 0, 0, 0, 0, 0);
        this._ground.setTransform(0, System.height / 2 / this.scale, 0.0);

        this._createPaddles();

        // create button, which shows menu
        this._menuBtn = new ImageButton ({
            image       : res.images.menuPart,
            x           : res.images.menuPart.width / 2,
            y           : System.height / 2,
            frameWidth  : res.images.menuPart.width,
            frameHeight : res.images.menuPart.height });
        this.add(this._menuBtn);
        
        this._menuBtn.onClick = function()
        {
            if (this super.menuDialog.showed)
                this super.menuDialog.hide();
            else
                this super.menuDialog.show();
        }

        this._menuDialog = new MenuLayer();
        this._winnerDialog = new WinnerLayer();

        var(w, h) = res.paints.scoreBlue.measureText("0");
        this._scoreW = w;
        this._scoreH = h;

        this._lastX = 0;
        this._lastY = 0;
        this._reseted = false;
    }

    // @region properties
    property winnerDialog(v)
    {
        get return this._winnerDialog;
    }

    property menuDialog(v)
    {
        get return this._menuDialog;
    }

    property ground(v)
    {
        get return this._ground;
    }

    property puck(v)
    {
        get return this._puck;
    }
    // @endregion

    // @region private functions
    function _createPaddles()
    {
        // create AI player
        this.paddleAI = this._createPaddle(#playerAI);
        this.paddleAI.endContact = function(body)
        {
            this super.playerAI.hit();
        }
        this.playerAI = new PlayerAI(this, this.paddleAI);

        // create human player
        this.paddleHuman = this._createPaddle(#playerHuman);
        this.playerHuman = new PlayerHuman(this, this.paddleHuman);
    }

    // creates paddle (for AI or human player)
    function _createPaddle(paddleType)
    {
        assert paddleType == #playerAI || paddleType == #playerHuman;
        const density = 1.1, friction = 0.3, bounce = 0.0;
        var paddle = (paddleType == #playerAI)
            ? this.addCircleBody(res.images.playerAI, #dynamic, density, friction, bounce, res.images.playerAI.width / 2)
            : this.addCircleBody(res.images.playerHuman, #dynamic, density, friction, bounce, res.images.playerHuman.width / 2);
        paddle.fixedRotation = true;
        paddle.setLinearDamping(5.0);
    
        return paddle;
    }

    // created puck object
    function _createPuck()
    {
        const density = 1.0, friction = 0.2, bounce = 0.4;
        // create physics body of the puck
        var puck = this.addCircleBody(res.images.puck, #dynamic, density, friction, bounce,  res.images.puck.width / 2/*radius*/);
        // place puck to center of the table
        puck.setPosition(System.width / 2, System.height / 2);
        puck.fixedRotation = true;
        puck.bullet = true;
        puck.setLinearDamping(0.3);
        return puck;
    }

    // creates goals
    function _createGoals()
    {
        const density = 0.0, friction = 0.0, bounce = 0.0;
    
        var goalA = this.addPolygonBody(null, #static, density, friction, bounce, this.goalsWidth, System.width / 32);
        goalA.beginContact = function(contact) { this super._checkGoal(contact, #playerAI); }
        goalA.setPosition(System.width / 2, -2 * this.puckRadius + System.width / 32);
    
        var goalALeft = this.addPolygonBody(null, #static, density, friction, bounce, System.width / 32, 2 * this.puckRadius);
        goalALeft.beginContact = function(contact) { this super._checkGoal(contact, #playerAI); }
        goalALeft.setPosition(System.width / 2 - this.goalsWidth / 2 - this.puckRadius, -1 * this.puckRadius);
    
        var goalARight = this.addPolygonBody(null, #static, density, friction, bounce, System.width / 32, 2 * this.puckRadius);
        goalARight.beginContact = function(contact) { this super._checkGoal(contact, #playerAI); }
        goalARight.setPosition(System.width / 2 + this.goalsWidth / 2 + this.puckRadius, -1 * this.puckRadius);
    
        var goalB = this.addPolygonBody(null, #static, density, friction, bounce, this.goalsWidth, System.width / 32);
        goalB.beginContact = function(contact) { this super._checkGoal(contact, #playerHuman); };
        goalB.setPosition(System.width / 2, System.height + 2 * this.puckRadius - System.width / 32);
    
        var goalBLeft = this.addPolygonBody(null, #static, density, friction, bounce, System.width / 32, 2 * this.puckRadius);
        goalBLeft.beginContact = function(contact) { this super._checkGoal(contact, #playerHuman); };
        goalBLeft.setPosition(System.width / 2 - this.goalsWidth / 2 - this.puckRadius, System.height + this.puckRadius);
    
        var goalBRight = this.addPolygonBody(null, #static, density, friction, bounce, System.width / 32, 2 * this.puckRadius);
        goalBRight.beginContact = function(contact) { this super._checkGoal(contact, #playerHuman); };
        goalBRight.setPosition(System.width / 2 + this.goalsWidth / 2 + this.puckRadius, System.height + this.puckRadius);
    }

    function _createBarriers()
    {
        const density = 0.0, friction = 0.0, bounce = 1.0;
        const width = System.width / 4, height = System.width / 32;
    
        var topWallA = this.addPolygonBody(null, #static, density, friction, bounce, width, height);
        topWallA.setPosition(System.width / 8, 1);
    
        var topWallB = this.addPolygonBody(null, #static, density, friction, bounce, width, height);
        topWallB.setPosition(System.width - System.width / 8, 1);
    
        var bottomWallA = this.addPolygonBody(null, #static, density, friction, bounce, width, height);
        bottomWallA.setPosition(System.width/8, System.height);
    
        var bottomWallB = this.addPolygonBody(null, #static, density, friction, bounce, width, height);
        bottomWallB.setPosition(System.width - System.width / 8, System.height);
    
        var leftWall = this.addPolygonBody(null, #static, density, friction, bounce, System.width / 32, System.height);
        leftWall.setPosition(1, System.height / 2);
    
        var rightWall = this.addPolygonBody(null, #static, density, friction, bounce, System.width / 32, System.height);
        rightWall.setPosition(System.width, System.height / 2);
    
        // corners
        var leftTop = this.addPolygonBody(null, #static, density, friction, bounce, this.puckRadius, this.puckRadius);
        leftTop.setPosition(this.puckRadius / 2, System.width / 60);
        var rightTop = this.addPolygonBody(null, #static, density, friction, bounce, this.puckRadius, this.puckRadius);
        rightTop.setPosition(System.width - this.puckRadius / 2, System.width / 60);
        var leftBottom = this.addPolygonBody(null, #static, density, friction, bounce, this.puckRadius, this.puckRadius);
        leftBottom.setPosition(this.puckRadius / 2, System.height - System.width / 60);
        var rightBottom = this.addPolygonBody(null, #static, density, friction, bounce, this.puckRadius, this.puckRadius);
        rightBottom.setPosition(System.width - this.puckRadius / 2, System.height - System.width / 60);
    }

    // listener for begin of collicion
    function _beginContact(contact)
    {
        var bodyA = contact.getBodyA();
        var bodyB = contact.getBodyB();

        if(bodyA.beginContact)
            bodyA.beginContact(bodyB, contact);
        if(bodyB.beginContact)
            bodyB.beginContact(bodyA, contact);
    }

    // listener for end of collicion
    function _endContact(contact)
    {
        var bodyA = contact.getBodyA();
        var bodyB = contact.getBodyB();

        if(bodyA.endContact)
            bodyA.endContact(bodyB, contact);
        if(bodyB.endContact)
            bodyB.endContact(bodyA, contact);
    }

    // checks goal
    function _checkGoal(contact, player)
    {
        assert player == #playerHuman || player == #playerAI;

        if (contact == this._puck) {
            if (player == #playerAI) {
                this.playerAI.win();
                if (this.playerAI.score > 6) {
                    this.winnerDialog.winner = "BLUE";
                    this.winnerDialog.show();
                    this.paused = true;
                    this._win = true;
                }
            } else {
                this.playerHuman.win();
                if (this.playerHuman.score > 6) {
                    this.winnerDialog.winner = "GREEN";
                    this.winnerDialog.show();
                    this.paused = true;
                    this._win = true;
                }
            }
            this.paused = true;
            this._goal = true;
        }
    }
    // @endregion

    // @region public functions
    // @region system events
    function process()
    {
        super.process();

        // read for new game?
        if (this._goal) {
            this._goal = false;
            this.reset();
        }

        // skip when game paused
        if (this.paused)
            return;

        // simulate AI
        this.playerAI.handleProcess();

        // do physics math
        this.step(1.0 / 40.0, 4, 8);
        game.invalidate();
    }

    // drawing functinos (overloaded ancestor function)
    function draw(canvas)
    {
        canvas.drawBitmapRect(res.images.background, 0, 0, res.images.background.width, res.images.background.height, 0, 0, System.width, System.height);
    
        // save current canvas setings
        canvas.save();
        // rotate canvas to 270° CW
        canvas.rotate(270);
        // draw score
        canvas.drawText(this.playerAI.score.toString(), System.height / - 2 - 2 * this._scoreW, System.width / 16 + this._scoreH, res.paints.scoreBlue);
        canvas.drawText(this.playerHuman.score.toString(), System.height / - 2 + this._scoreW, System.width / 16 + this._scoreH, res.paints.scoreGreen);
        // restore canvas settings (revert rotation)
        canvas.restore()
    
        super.draw(canvas);
    }
    // @endregion

    // @region user events
    // called when touch down occured
    function pointerPressed(x, y)
    {
        super.pointerPressed(x, y);

        if (y > System.height / 2 - res.images.menuPart.height / 2  && y < System.height / 2 + res.images.menuPart.height / 2 && x < res.images.menuPart.width)
            return;

        if (!this.paused) {
            this.playerHuman.handlePressed(x, y);
        }
    }

    // called when touch drag occured
    function pointerDragged(x, y)
    {
        super.pointerDragged(x, y);

        if (this._reseted) {
            this._reseted = false;

            this.playerHuman.handleReleased(x, y);
            this.playerHuman.handlePressed(x, y);
        }
        this._lastX = x;
        this._lastY = y;

        if (!this.paused) {
            this.playerHuman.handleDragged(x, y);
        }
    }

    // called when touch up occured
    function pointerReleased(x, y)
    {
        super.pointerReleased(x, y);

        if (!this.paused) {
            this.playerHuman.handleReleased(x, y);
        }
    }
    // @endregion

    // reset or start new game
    function reset(newGame = false)
    {
        this.destroyBody(this.puck);
        if (this._win) {
            this._win = false;
            return;
        }
        game.gameScene.gameStarted = true;
        this._puck = this._createPuck();
        // reset player (also score if needed)
        this.playerAI.reset(newGame);
        this.paused = false;
        if (!newGame)
            this._reseted = true;
        else {
            this.playerAI.reset();
            this.playerHuman.reset();
        }
    }

    // converts x-coord from screen to box2d
    function x2box2d(x)
    {
        return x / this._world.scale;
    }

    // converts y-coord from screen to box2d
    function y2box2d(y)
    {
        return (System.height - y) / this._world.scale;
    }
    // @endregion
}
include "app://scenes/button.ms"

class MenuLayer : Layer
{
    function init()
    {
        super.init();
        // gap between two menu buttons
        var gap = (res.images.menuBg.height - 3*res.images.menuButton.height)/8;
        var height = res.images.menuButton.height;
        this.paint = new Paint();
        this.height = System.height;
        this.width = System.width;

        // buttons
        var newGame = new AirHockeyButton({x : System.width / 2, y : (System.height - res.images.menuBg.height)/2 + height / 2 + 2*gap, text : "START GAME"});
        newGame.onClick = function(){game.gameScene.reset(true); this super.hide();};
        this.add(newGame);

        this._continueGame = new AirHockeyButton({x : System.width / 2, y : (System.height - res.images.menuBg.height)/2 + 3*height / 2 + 3*gap, text : "RESUME"});
        this._continueGame.onClick = function(){
            if (this.enabled)
                this super.hide();
        };
        this.add(this._continueGame);

        if (System.OS_NAME != #iOS) {
            var quit = new AirHockeyButton({x : System.width / 2, y : (System.height - res.images.menuBg.height)/2 + 5*height / 2 + 4*gap, text : "QUIT"});
            quit.onClick = function(){game.quit();};
            this.add(quit);
        }

        // is menu displayed or not?
        this.showed = false;
    }

    // @region public functions
    function draw(canvas)
    {
        canvas.drawRect(0, 0, System.width, System.height, this.paint);
        canvas.drawBitmap(res.images.menuBg, (System.width - res.images.menuBg.width) / 2, (System.height - res.images.menuBg.height) / 2);

        super.draw(canvas);
    }

    function show()
    {
        game.gameScene.add(this);
        this.showed = true;
        if (game.gameScene.gameStarted)
            this._continueGame.enabled = true;
        else
            this._continueGame.enabled = false;
        game.gameScene.paused = true;
        this.paint.color = 0x00000000;
        var animator = new Animator({
            transition : Animator.Transition.easyIn, // start up slowly and then quickly speed up at the end of the animation
            duration : 1300,                     // length of animation in miliseconds
        });
        animator.addSubject(function(state) {      // state starts from 1.0 to 0.0
           var self = this super;
           self.paint.alpha = (180*state).toInteger();
        })
        animator.play();
    }

    function hide()
    {
        this.showed = false
        game.gameScene.detach(this);
        game.gameScene.paused = false;
    }
    // @endregion
}
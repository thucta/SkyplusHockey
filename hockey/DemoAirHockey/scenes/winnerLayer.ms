include "app://scenes/button.ms"

class WinnerLayer : Layer
{
    function init()
    {
        super.init();
        this.height = System.height;
        this.width = System.width;
        this._winner = "";

        var gap = (res.images.menuBg.height - 3*res.images.menuButton.height)/8;
        this.paint = new Paint();

        // buttons
        var menu = new AirHockeyButton({x : System.width / 2, y : System.height / 2 + res.images.menuButton.height, text : "MENU" });
        menu.onClick = function(){this super.hide(); game.gameScene.menuDialog.show();};
        this.add(menu);
    }

    // @region properties
    property winner(v)
    {
        get return this._winner;
        set this._winner = v;
    }
    // @endregion

    // @region functions
    function show()
    {
        game.gameScene.add(this);
        game.gameScene.paused = true;
        game.gameScene.gameStarted = false;
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
        game.gameScene.detach(this);
        game.gameScene.paused = false;
    }

    function draw(canvas)
    {
        canvas.drawRect(0, 0, System.width, System.height, this.paint);
        canvas.drawBitmap(res.images.menuBg, (System.width - res.images.menuBg.width) / 2, (System.height - res.images.menuBg.height) / 2);

        var top = (System.height - res.images.menuBg.height)/2;
        var height = res.images.menuBg.height;

         var (w, h) = res.paints.winDialog.measureText("YEAH, THE " + this.winner);
        canvas.drawText("YEAH, THE " + this.winner, System.width / 2 - w/2, top + 2 * h, res.paints.winDialog );
        (w, h) = res.paints.winDialog.measureText("WINS");
        canvas.drawText("WINS", System.width / 2 - w / 2, top + 4 * h,  res.paints.winDialog );

        super.draw(canvas);
    }
    // @endregion
}
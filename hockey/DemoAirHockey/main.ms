include "lib://game2d/game.ms"
include "lib://game2d/layer.ms"
include "lib://box2d/physicsScene.ms"
include "lib://game2d/gameButton.ms"

include "app://scenes/gameScene.ms"
include "app://resources.ms"
include "app://playerAI.ms"
include "app://playerHuman.ms"

// global variables
var game = new Game();
var res = new Resources();

game.onStart = function(sender)
{
    if (res.supportedResolution) {
        // push game scene to to application
        this.gameScene = new GameScene();
        this.push(this.gameScene);
        // restart game (start new)
        this.gameScene.reset();
    }
    this._paint = new Paint();
    this._paint.textSize = System.height / 30;
    var (w, h) = this._paint.measureText("Unsupported resolution");
    this._textWidth = w;
}

game.onDraw = function(sender, canvas)
{
    canvas.clear(0xffffffff);
    canvas.drawText("Unsupported resolution", (System.width - this._textWidth) / 2, System.height / 2, this._paint);
}

// @region user events
// quit game when user clicks on the back or home hardware button
game.onKeyPressed = function(sender, keyCode)
{
    if (keyCode == #back || keyCode == #home)
        game.quit();
}

game.onPointerPressed = function()
{
    if (!res.supportedResolution)
        game.quit();
}
// @endregion

// run main loop
game.run(true);
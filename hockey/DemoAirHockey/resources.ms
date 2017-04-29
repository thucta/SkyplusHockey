class Resources
{
    function this()
    {
        this._supportedResolution = true;
        this._images = {
            background          : this._loadImage("backGame", "jpg");
            playerHuman         : this._loadImage("player2");
            playerAI            : this._loadImage("player1");
            puck                : this._loadImage("puck");
            menuBg              : this._loadImage("menuBack");
            menuButton          : this._loadImage("menuBtn");
            menuButtonPressed   : this._loadImage("menuBtnPress");
            menuPart            : this._loadImage("menuPart");
        };
        if (this._supportedResolution)
            this._paints = {
                menuButton          : this._createPaint(0xffffffff, 2 * this.images.menuButton.height / 3);
                menuButtonDisabled  : this._createPaint(0x7fffffff, 2 * this.images.menuButton.height / 3);
                scoreGreen          : this._createPaint(0xff238223, 2 * this.images.menuButton.height / 3);
                scoreBlue           : this._createPaint(0xff1969A5, 2 * this.images.menuButton.height / 3);
                winDialog           : this._createPaint(0xff000000, 1 * this.images.menuButton.height / 3);
            }
    }

    // @region properties
    property images(v)
    {
        get return this._images;
    }

    property paints(v)
    {
        get return this._paints;
    }

    property integers(v)
    {
        get return this._integers;
    }

    property supportedResolution(v)
    {
        get return this._supportedResolution
    }
    // @endregion

    // @region private functions
    function _loadImage(filename, format = "png")
    {
        var file = "app://" + System.width + "_" + System.height + "/" + filename + "." + format;
        var bitmap;
        if (System.isFile(file)) {
             bitmap = Bitmap.fromFile(file);
             if (bitmap != null)
                return bitmap;
        }
    
        // Kindle Fire 600x1002
        if (System.width == 600) {
            file = "app://" + System.width + "_1024" + "/" + filename + "." + format;
            bitmap = Bitmap.fromFile(file);
            if (bitmap != null)
                return bitmap;
        }
        // Galaxy tab 800x1232 752x1280
        if (System.width == 800) {
            file = "app://" + System.width + "_1280" + "/" + filename + "." + format;
            bitmap = Bitmap.fromFile(file);
            if (bitmap != null)
                return bitmap;
        }
        // SE xperia 480x854
        if (System.width == 480) {
            file = "app://" + System.width + "_800" + "/" + filename + "." + format;
            bitmap = Bitmap.fromFile(file);
            if (bitmap != null)
                return bitmap;
        }

        this._supportedResolution = false;
        return null;
    }

    function _createPaint(color, textSize)
    {
        var paint = new Paint();
        paint.color = color;

        paint.textSize = 2 * this.images.menuButton.height / 3;
        return paint;
    }
    // @endregion
}
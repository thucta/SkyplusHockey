class AirHockeyButton : ImageButton
{
    // constructor
    function init()
    {
        super.init();
        // private properties
        this._text = "";
        this._enabled = true;
        this._textW = 0;
        this._textH = 0;

        this.image = res.images.menuButton;
        this.height = res.images.menuButton.height;
        this.width = res.images.menuButton.width;
    }

    //@region properties
    property text(v)
    {
        get return this._text;
        set {
            this._text = v;
            var (w, h) = res.paints.menuButton.measureText(v);
            this._textW = w;
            this._textH = h;
        }
    }

    property enabled(v)
    {
        get return this._enabled;
        set this._enabled = v;
    }
    //@endregion

    // @region public functions
    function draw(canvas)
    {
//        super._setObject();

        if (this._clicked || !this.enabled)
            canvas.drawBitmapNine(res.images.menuButtonPressed, this.x - this.width / 2, this.y - this.height / 2, this.x + this.width / 2, this.y + this.height / 2);
        else
            canvas.drawBitmapNine(res.images.menuButton, this.x - this.width / 2, this.y - this.height / 2, this.x + this.width / 2, this.y + this.height / 2);

        if (this.enabled)
            canvas.drawText(this.text, this.x - this._textW / 2, this.y + this._textH / 2, res.paints.menuButton);
        else
            canvas.drawText(this.text, this.x - this._textW / 2, this.y + this._textH / 2, res.paints.menuButtonDisabled);
    }
    // @endregion
}
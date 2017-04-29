// Ancestor for human and AI player
class Player
{
    // constructor
    function this(table, paddle)
    {
        // set owner
        this._table = table;
        // set physics body
        this._paddle = paddle;
        // helper
        this._puckRadius = res.images.playerAI.width / 2;
        // reset score counter, etc
        this.reset();
    }

    // @region properties
    // gets score of the player
    property score(v) { get return this._score; }

    // gets table (owner)
    property table(v) { get return this._table; }

    // gets paddle
    property paddle(v) { get return this._paddle; }

    // gets radius of the puck
    property puckRadius(v) { get return this._puckRadius; }
    // @endregion

    // @region public functions
    // called when this player wins the round
    function win()
    {
        this._score++;
    }

    // resets player (and/or it's score)
    function reset(clearScore = true)
    {
        if (clearScore)
            this._score = 0;
    }
    // @endregion
}
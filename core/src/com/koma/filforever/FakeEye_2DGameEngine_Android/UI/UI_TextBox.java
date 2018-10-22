package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;

public class UI_TextBox // extends UI_IMenuControll
{
    /*public String Header;
    public String Question;

    public String EnteredText;

    public FloatController ApearDisapearController;

    public Color Color1;
    public Color Color2;

    public Color TextColor;
    public String FontName;

    public Vector2 Position;
    public Vector2 Size;

    public Vector2 TextFieldSize;
    public Vector2 TextFieldPivot;
    public float TextFieldButtonWidth;
    public float TextFieldButtonHeight;
    public float ButtonSpacing;

    public List<String> latters1 = new List<String>() { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P" };
    public List<String> latters2 = new List<String>() { "A", "S", "D", "F", "G", "H", "J", "K", "L" };
    public List<String> latters3 = new List<String>() { "Z", "X", "C", "V", "B", "N", "M" };

    public UI_TextBox(String Header, String Question, Vector2 Size)
    {
        super();

        ButtonSpacing = 4;
        TextFieldSize = new Vector2(Size.X - 30, 330);
        TextFieldPivot = new Vector2(20, 470);

        TextFieldButtonWidth = TextFieldSize.X / 10 - ButtonSpacing;
        TextFieldButtonHeight = 200 / 3 - ButtonSpacing;

        EnteredText = "";
        this.Header = Header;
        this.Question = Question;

        ApearDisapearController = new FloatController(5.0f);

        Position = Vector2.Zero;
        this.Size = Size;

        ControllPosition = Vector2.Zero;
        ControllSize = Size;

        Color1 = Color.LightGray;
        Color2 = Color.DarkGray;

        TextColor = Color.White;
        FontName = "";
    }

    public Boolean InUnderControll(Vector2 SomePosition)
    {
        return EngineService.I.isInRect(SomePosition, Position.sub(Size.div(2)), Size);
    }

    public override void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName)
    {
        if (InUnderControll(ToouchPosition))
        {
            ControllTouchEventCall(ToouchPosition, TouchEventName, null);
            ApearDisapearController.Value = 1;
        }
    }

    public override void Update(float timeDelta)
    {
        ApearDisapearController.Update(timeDelta);

        if (InUnderControll(TouchService.I.TouchPosition))
            ApearDisapearController.GoUp();
        else
            ApearDisapearController.GoDown();
    }

    public override void Draw(SpriteAdapter spriteBatch)
    {
        Draw(spriteBatch, Vector2.Zero);
    }

    public override void Draw(SpriteAdapter spriteBatch, Vector2 Offset)
    {
        spriteBatch.FillRectangle(Position, Size, Color.Black, 0.8f, 0, 80);

        spriteBatch.FillRectangle(Position, new Vector2(Size.X, 150), Color.White, 1, 0, 81);
        spriteBatch.FillRectangle(TextFieldPivot.sub(20, -40), new Vector2(Size.X, 350), Color.White, 1, 0, 81);

        spriteBatch.FillRectangle(Position, new Vector2(Size.X, 150), Color.Black, 0.7f, 0, 81);
        spriteBatch.FillRectangle(TextFieldPivot.sub(20, -40), new Vector2(Size.X, 350), Color.Black, 0.7f, 0, 81);

        spriteBatch.DrawText(Header, new Vector2(Size.X / 2, 50), TextColor, 1, "", 1, TextAlign.CenterXY, 0, 83);
        spriteBatch.DrawText(Question, new Vector2(Size.X / 2, 70), TextColor, 1, "", 1, TextAlign.CenterXY, 0, 83);

        spriteBatch.FillRectangle(new Vector2(30, 100), new Vector2(Size.X - 60, 40), Color.White, 1, 0, 83);

        for (int i = 0; i < 10; i++)
        {
            Vector2 pos = TextFieldPivot.add(new Vector2(TextFieldButtonWidth * i + ButtonSpacing * i - ButtonSpacing / 2, TextFieldButtonHeight + ButtonSpacing / 2));
            spriteBatch.FillRectangle(pos, new Vector2(TextFieldButtonWidth - ButtonSpacing, TextFieldButtonHeight - ButtonSpacing), Color.Black, 0.9f, 0, 84);
            spriteBatch.DrawText(latters1[i], pos.sub(ButtonSpacing / 2, 0).add(new Vector2(TextFieldButtonWidth, TextFieldButtonHeight) / 2), TextColor, 1, "", 1, TextAlign.CenterXY, 0, 85);
        }

        for (int i = 0; i < 9; i++)
        {
            Vector2 pos = new Vector2(TextFieldButtonWidth / 2, TextFieldButtonHeight + ButtonSpacing).add(TextFieldPivot.add(new Vector2(TextFieldButtonWidth * i + ButtonSpacing * i - ButtonSpacing / 2, TextFieldButtonHeight + ButtonSpacing / 2)));
            spriteBatch.FillRectangle(pos, new Vector2(TextFieldButtonWidth - ButtonSpacing, TextFieldButtonHeight - ButtonSpacing), Color.Black, 0.9f, 0, 84);
            spriteBatch.DrawText(latters2[i], pos.sub(ButtonSpacing / 2, 0).add(new Vector2(TextFieldButtonWidth, TextFieldButtonHeight) / 2), TextColor, 1, "", 1, TextAlign.CenterXY, 0, 85);
        }

        for (int i = 0; i < 7; i++)
        {
            Vector2 pos = new Vector2(TextFieldButtonWidth + TextFieldButtonWidth / 2 + ButtonSpacing / 2, TextFieldButtonHeight * 2 + ButtonSpacing * 2).add(TextFieldPivot.add(new Vector2(TextFieldButtonWidth * i + ButtonSpacing * i - ButtonSpacing / 2, TextFieldButtonHeight + ButtonSpacing / 2)));
            spriteBatch.FillRectangle(pos, new Vector2(TextFieldButtonWidth - ButtonSpacing, TextFieldButtonHeight - ButtonSpacing), Color.Black, 0.9f, 0, 84);
            spriteBatch.DrawText(latters3[i], pos.sub(ButtonSpacing / 2, 0).add(new Vector2(TextFieldButtonWidth, TextFieldButtonHeight) / 2), TextColor, 1, "", 1, TextAlign.CenterXY, 0, 85);
        }
    }*/
}
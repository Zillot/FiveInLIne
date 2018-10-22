package com.koma.filforever.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.koma.filforever.FakeEye_2DGameEngine_Android.DebugTools.DebugService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_DialogMessage;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.SaveLoadService;
import com.koma.filforever.RootFolder.GamePages.SelectGameModePage;
import com.koma.filforever.RootFolder.GamePages.TopLeaderbordPage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import com.koma.filforever.RootFolder.GamePages.AchievementsPage;
import com.koma.filforever.RootFolder.GamePages.GameMenu;
import com.koma.filforever.RootFolder.GamePages.LanguageSelectPage;
import com.koma.filforever.RootFolder.GamePages.LeaderbordPage;
import com.koma.filforever.RootFolder.GamePages.LoadingScreen;
import com.koma.filforever.RootFolder.GamePages.MainMenu;
import com.koma.filforever.RootFolder.GamePages.ProfilePage;

public class MainGameClass {
    private static Random rand = new Random();
    public static MainGameClass SelftPointer;

    public static Boolean NeedToReLoad;
    private SpriteAdapter spriteAdapter;

    public Delegate LoadBigBunnerEvent;
    public Delegate ShowBigBunnerEvent;
    public Delegate RateRequestEvent;
    public Delegate ReLoadBannerEvent;
    public Delegate GetDeviceID;

    private Boolean seted;

    public UI_DialogMessage dialog;
    public Boolean dialogShowed;

    public MainGameClass() {
        dialogShowed = false;
        dialog = new UI_DialogMessage(Color.LightGray, Color.Black, "Message", "no message", new Vector2(480, 800), 60);
    }

    public Boolean ServerMessageDialog() {
        if (dialogShowed) {
            dialog.Hide();
            dialogShowed = false;
            return false;
        } else {
            return true;
        }
    }

    public void CheckMessages() {
        SaveLoadService.I.getMessage("", GetMessage_DownloadStringCompleted);
    }

    private Delegate GetMessage_DownloadStringCompleted = new Delegate() {
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            try {
                List<String> rootObject = new ArrayList<String>();
                ArrayList<String> list = new Json().fromJson(ArrayList.class, httpResponse.getResultAsString());

                for (String v : list) {
                    rootObject.add(v);
                }

                if (rootObject.size() == 0) {
                    return;
                }

                if (rootObject.size() > 0) {
                    dialog.SetText(rootObject.get(0), new Vector2(480, 800), "Message");
                    dialog.Show();

                    dialogShowed = true;
                }
            } catch (Exception ex) {

            }
        }
    };

    public void LoadBigBunner() {
        if (LoadBigBunnerEvent != null)
            LoadBigBunnerEvent.VoidDelegate();
    }

    public void RateRequest() {
        if (RateRequestEvent != null) {
            RateRequestEvent.VoidDelegate();
        }
    }

    public void ShowBigBunner() {
        if (ShowBigBunnerEvent != null) {
            ShowBigBunnerEvent.VoidDelegate();
        }
    }

    public void ReLoadBanner() {
        if (ReLoadBannerEvent != null) {
            ReLoadBannerEvent.VoidDelegate();
        }
    }

    public void Initialize() {
        seted = false;
        SelftPointer = this;

        spriteAdapter = new SpriteAdapter();
        SpriteAdapter.SelfPointer = spriteAdapter;

        //задаем размеры окна
        spriteAdapter.WindwoSize = new Vector2(480, 800);
        spriteAdapter.RealWindwoSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        spriteAdapter.calculateScaleAmount();

        NeedToReLoad = false;

        //Включение сервисов
        TouchService.I.EnableDebug();
        DebugService.I.ConnectToDebugToolServer();
        DebugService.I.EnableDebug();
        EngineService.I.BlankScrenObj.SetBlankColor(Color.Beige);

        //выщитываем размеры клеточки исходя из размеров поля
        DataControll.CellWidth = DataControll.FieldWidth / DataControll.Columns;
        DataControll.CellHeight = DataControll.FieldHeight / DataControll.Rows;

        SpriteAdapter.pixelSize = (int) (38.0f);// DataControll.CellWidth * SpriteAdapter.SelfPointer.ScaleAmount;
        fontloaded = false;
    }

    public Boolean BackPress() {
        if (EngineService.I.CurrentPageName.compareTo("MainMenu") == 0) {
            return true;
        } else {
            EngineService.I.NavigateTo("MainMenu", "", false);
        }

        return false;
    }

    public boolean fontloaded;

    //перезагрузка контента, необходимо в случии возврата к приложению на вин фоне или андроид
    public void ReloadContent() {
        //инициализация
        spriteAdapter.Initialize();

        //загрузка возможных языков
        spriteAdapter.AvailableLanguages.add("EN");
        spriteAdapter.AvailableLanguages.add("UA");
        spriteAdapter.AvailableLanguages.add("RU");

        //Установка стандартного
        spriteAdapter.Language = spriteAdapter.AvailableLanguages.get(0);

        //загрузка слов на разных язиках   Ключ             EN               UA             RU
        spriteAdapter.addLanguageString("Continue", "Continue", "Продовжити", "Продолжить");
        spriteAdapter.addLanguageString("Start Game", "Start Game", "Нова гра", "Новая игра");
        spriteAdapter.addLanguageString("Achievements", "Achievements", "Досягнення", "Достижения");
        spriteAdapter.addLanguageString("Leaderboard", "Leaderboard", "Таблиця рекордів", "Таблица рекордов");
        spriteAdapter.addLanguageString("Mode", "Mode", "Тип", "Тип");
        spriteAdapter.addLanguageString("Lines", "Lines", "Лінії", "Линии");
        spriteAdapter.addLanguageString("Squares", "Squares", "Квадрати", "Квадраты");
        spriteAdapter.addLanguageString("Snakes", "Snakes", "Змійка", "Змейка");
        spriteAdapter.addLanguageString("Circles", "Circles", "Кільця", "Кольца");
        spriteAdapter.addLanguageString("YOUR", "YOUR", "ВАШ", "ВАШ");
        spriteAdapter.addLanguageString("NEW", "NEW", "НОВИЙ", "НОВЫЙ");
        spriteAdapter.addLanguageString("BEST", "BEST", "КРАЩИЙ", "ЛУЧШИЙ");
        spriteAdapter.addLanguageString("SCORE", "SCORE", "РАХУНОК", "СЧЕТ");
        spriteAdapter.addLanguageString("Hello, ", "Hello, ", "Привіт, ", "Привет, ");

        spriteAdapter.addLanguageString("Final score", "Final score", "Фінальний рахунок", "Конечный счет");
        spriteAdapter.addLanguageString("Game Over", "Game Over", "Кінець Гри", "Конец Игры");
        spriteAdapter.addLanguageString("Tap to restart", "Tap to restart", "Тапніть для перезапуску", "Тапните для перезапуска");
        spriteAdapter.addLanguageString("Or press Back to return to main", "Or press Back to return to main", "Або натисніть назад для виходу", "Или нажмите назад для выхода");

        spriteAdapter.addLanguageString("Change your name", "Change your name", "Змінить ваше ім'я", "Смените ваше имя");
        spriteAdapter.addLanguageString("Back To Main", "Back To Main", "До головної сторінки", "На главную страницу");
        spriteAdapter.addLanguageString("Still in development", "Still in development", "Ще в розробці", "Еще в разработке");
        spriteAdapter.addLanguageString("Stay tuned)", "Stay tuned)", "Чекайте найближчим часом)", "Ждите в ближайшее время)");
        spriteAdapter.addLanguageString("Attention!", "Attention!", "Увага!", "Внимание!");
        spriteAdapter.addLanguageString("Please rate this game", "Please rate this game", "Будь ласка, поставте оцінку цій грі", "Пожалуйста поставьте оценку это игре");
        spriteAdapter.addLanguageString("you help us a lot by doing so", "you help us a lot by doing so", "ви нам дуже допоможете зробивши це", "вы нам очень поможете сделав это");
        spriteAdapter.addLanguageString("Player name", "Player name", "Ім'я гравця", "Имя игрока");
        spriteAdapter.addLanguageString("Enter your name or nickname", "Enter your name or nickname", "Введіть своє ім'я або псевдонім", "Введите свое имя или псевдоним");
        spriteAdapter.addLanguageString("Select", "Select", "Вибрати", "Выбрать");
        spriteAdapter.addLanguageString("SelModLine", "Select game mode", "Виберіть режим гри", "Вибирите режим игры");
        spriteAdapter.addLanguageString("Connecting", "Connecting", "Підключення", "Подключение");
        spriteAdapter.addLanguageString("No scores", "No scores", "Немає записів", "Нет записей");

        spriteAdapter.addLanguageString("Can't load leaderbord", "Can't load leaderboard", "Неможу завантажити таблицю рекордів", "Немогу загрузить таблицу рекордов");
        spriteAdapter.addLanguageString("Please check your", "Please check your", "Будь-ласка перевірте ваше", "Пожалуйста проверьте ваше");
        spriteAdapter.addLanguageString("internet connection", "internet connection", "підключення до мережі", "подключение к сети");
        spriteAdapter.addLanguageString("Loading", "Loading", "Завантаження", "Загрузка");
        spriteAdapter.addLanguageString("Cancel", "Cancel", "Ніколи в житті!", "Ни за что!");
        spriteAdapter.addLanguageString("Ok", "Ok", "Добре", "Хорошо");
        spriteAdapter.addLanguageString("SoundOn", "Sound is On", "Звук  ввімкнений", "Звук включен");
        spriteAdapter.addLanguageString("SoundOff", "Sound is Off", "Звук  вимкнений", "Звук выключен");
        spriteAdapter.addLanguageString("Profile", "Profile", "Профіль", "Профиль");
        spriteAdapter.addLanguageString("Setups", "Setups", "Налаштування", "Настройки");

        spriteAdapter.addLanguageString("ToNextModLine", "Go to next mode", "Перейдемо до наступного режиму", "Перейдем к следующему режиму");
        spriteAdapter.addLanguageString("TapAnywhereToContinue", "Tap anywhere to continue", "Торкніться екрана в будь-якому місці щоб продовжити", "Коснитесь экрана в любом месте чтобы продолжить");
        spriteAdapter.addLanguageString("MainMenuLine1", "Welcome to \'Five In Line\' player!", "Ласкаво просимо в \'Five In Line\'!", "Добро пожаловать в \'Five In Line\'!");
        spriteAdapter.addLanguageString("MainMenuLine2", "Here is short introduction tutorial for you", "Ось інструкція для вас перед початком гри, це займе не більше 2 хвилинок)", "Вот инструкция для вас перед началом игры, это займет не больше 2 минут)");
        spriteAdapter.addLanguageString("MainMenuLine4", "If you want to change game language, press this button", "Якщо ви хочете змінити мову гри, натисніть цю кнопку", "Если вы хотите изменить язык игры, нажмите эту кнопку");
        spriteAdapter.addLanguageString("MainMenuLine5", "If you want turn off sound, press this button", "Якщо ви хочете відключити звук, натисніть цю кнопку", "Если вы хотите отключить звук, нажмите эту кнопку");
        spriteAdapter.addLanguageString("MainMenuLine6", "Also you can do it during the game", "Також ви можете зробити це під час гри", "Также вы можете сделать это во время игры");
        spriteAdapter.addLanguageString("MainMenuLine7", "Now please press this button to set up profile", "Тепер, будь ласка, натисніть цю кнопку, щоб налаштувати профіль", "Теперь, пожалуйста, нажмите эту кнопку, чтобы настроить профиль");
        spriteAdapter.addLanguageString("MainMenuLine8", "Great, now you ready to play, just tap this button", "Чудово, тепер ви готові грати, просто натисніть на цю кнопку", "Отлично, теперь вы готовы играть, просто нажмите на эту кнопку");
        spriteAdapter.addLanguageString("MainMenuLine9", "You should to set your name or nickname", "Ви повинні встановити своє ім'я або псевдонім", "Вы должны установить свое имя или псевдоним");
        spriteAdapter.addLanguageString("MainMenuLine10", "Super! Now go back to main page", "Супер! Тепер повернемося до основної сторінки", "Супер! Теперь вернемся к основной странице");
        spriteAdapter.addLanguageString("SelecModText", "You need to select one of follow  game modes", "Ви повинні вибрати один з доступних режимів гри", "Вы должны выбрать один из доступных режимов игры");
        spriteAdapter.addLanguageString("LinModRules", "In Line mode you need to build lines of 5 or more cubes, more cubes in line - more score you will get", "У режимі ліній, необхідно будувати лінії з 5 або більше кубиків, чим більше кубів на лінії тим більше балів ви отримаєте", "В режиме линий, необходимо строить линии из 5 или более кубиков, чем больше кубов в линии тем больше очков вы получите");
        spriteAdapter.addLanguageString("SquaModRuls", "In Squares mode you need to build rectangles 2x2, 2x3 or 3x3, more cubes in on rect - more score you will get", "У режимі квадратів, вам потрібно буде будувати прямокутники або квадрати 2x2, 2x3 чи 3x3, чим більша фігура тим більше балів ви отримаєте", "В режиме квадратов, вам нужно будет строить прямоугольники или квадраты 2x2, 2x3 или 3x3, чем больше фигура тем больше очков вы получите");
        spriteAdapter.addLanguageString("SnakModRuls", "In Snakes mode you need to build snakes... just place cubes near each other (diagonals not counting) end if you get length of figure more or equal then 7 it will blow, more cubes in on snake - more score you will get", "У режимі змійки, вам потрібно буде будувати змійок... просто розташовуйте кубики поряд один з одним (по діагоналі не рахується) якщо довжина більше або дорівнюе 7-ми - вони взорвуться! Чим більша фігура тим більше балів ви отримаєте", "В режиме змейки, вам вам нужно будет собирать змеек... просто располагайте кубики рядом друг с другом (по диагонали не считается) если длина больше или равна 7-ми - они взорвуться! Чем больше фигура тем больше очков вы получите");
        spriteAdapter.addLanguageString("CircModRuls", "In Squares mode you need to build rectangles 2x2, 2x3 or 3x3, more cubes in on rect - more score you will get", "У режимі квадратів, вам потрібно буде будувати прямокутники або квадрати 2x2, 2x3 чи 3x3, чим більша фігура тим більше балів ви отримаєте", "В режиме квадратов, вам нужно будет строить прямоугольники или квадраты 2x2, 2x3 или 3x3, чем больше фигура тем больше очков вы получите");

        //GAMEUPDATE blow, взривати, взрывать
        spriteAdapter.addLanguageString("MultiplyHintText", "Try to blow figure with one move, if you do this you will get multiply, more multiply - more scores you get!", "Намагайтеся взривати фігури одним рухом, якщо ви зробите це, ви отримаєте множення, більше множення - більше балів!", "Старайтесь взрывать фигуры одним движением, если вы сделаете это, вы получите умножение, больше умножение - больше очков!");
        spriteAdapter.addLanguageString("LastLineText", "Have fun!) And tap anywhere to end tutorial, good luck!)", "Насолоджуйтесь!) Торкніться екрана в будь-якому місці щоб завершити, успіхів!)", "Веселитесь!) Коснитесь экрана в любом месте чтобы завершить, удачи!)");
        spriteAdapter.addLanguageString("Repeat tutorial", "Repeat tutorial", "Повторити керівництво", "Повторить руководство");
        spriteAdapter.addLanguageString("LanguageLine1", "Please select language", "Виберіть мову", "Выберите язык");

        spriteAdapter.addLanguageString("Clear data", "Clear data ", "Відалити данні", "Удалить даные");


        //загрузка шрифтов
        if (!fontloaded) {
            LoadFont();
        }

        //загрузка звуков
        spriteAdapter.Sounds.put("Tap1", Gdx.audio.newSound(Gdx.files.internal("sounds/Tap1.wav")));
        spriteAdapter.Sounds.put("Tap2", Gdx.audio.newSound(Gdx.files.internal("sounds/Tap2.wav")));
        spriteAdapter.Sounds.put("Blow1", Gdx.audio.newSound(Gdx.files.internal("sounds/Blow1.wav")));
        spriteAdapter.Sounds.put("Blow2", Gdx.audio.newSound(Gdx.files.internal("sounds/Blow2.wav")));
        spriteAdapter.Sounds.put("Blow3", Gdx.audio.newSound(Gdx.files.internal("sounds/Blow3.wav")));
        spriteAdapter.Sounds.put("GameOver", Gdx.audio.newSound(Gdx.files.internal("sounds/GameOver.wav")));
        spriteAdapter.Sounds.put("NewBestRecord", Gdx.audio.newSound(Gdx.files.internal("sounds/NewBestRecord.wav")));
        spriteAdapter.Sounds.put("IllegalMove", Gdx.audio.newSound(Gdx.files.internal("sounds/IllegalMove.wav")));
        spriteAdapter.Sounds.put("Meow", Gdx.audio.newSound(Gdx.files.internal("sounds/Meow.wav")));

        //загрузка текстур
        Pixmap pixmap;
        pixmap = new Pixmap((int) SpriteAdapter.pixelSize, (int) SpriteAdapter.pixelSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.White.GetColor());
        pixmap.fill();
        spriteAdapter.Textures.put("PixelForRect", new Texture(pixmap));

        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.White.GetColor());
        pixmap.fill();
        spriteAdapter.Textures.put("Pixel", new Texture(pixmap));
        pixmap.dispose();

        spriteAdapter.Textures.put("KomaLogo", new Texture(Gdx.files.internal("Images/KomaLogo.png")));
        spriteAdapter.Textures.put("HeightlightItem", new Texture(Gdx.files.internal("Images/HeightlightItem.png")));
        spriteAdapter.Textures.put("HeightlightItemUper", new Texture(Gdx.files.internal("Images/HeightlightItemUper.png")));
    }

    public void LoadFont() {
        spriteAdapter.Fonts = new TreeMap<String, BitmapFont>();

        fontloaded = true;

        MyFontFactory factory = new MyFontFactory();

        spriteAdapter.Fonts.put("Arial10", factory.getFont("fonts/arial.ttf", 16.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("Arial12", factory.getFont("fonts/arial.ttf", 18.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("Arial16", factory.getFont("fonts/arial.ttf", 22.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("Arial20", factory.getFont("fonts/arial.ttf", 26.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("Arial24", factory.getFont("fonts/arial.ttf", 30.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("Arial32", factory.getFont("fonts/arial.ttf", 38.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("Arial50", factory.getFont("fonts/arial.ttf", 50.0f * spriteAdapter.ScaleAmount));

        spriteAdapter.Fonts.put("BArial10", factory.getFont("fonts/arialbd.ttf", 16.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("BArial20", factory.getFont("fonts/arialbd.ttf", 26.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("BArial24", factory.getFont("fonts/arialbd.ttf", 30.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("BArial32", factory.getFont("fonts/arialbd.ttf", 38.0f * spriteAdapter.ScaleAmount));
        spriteAdapter.Fonts.put("BArial72", factory.getFont("fonts/arialbd.ttf", 78.0f * spriteAdapter.ScaleAmount));
    }

    //загрузка контента
    public void LoadContent() {
        //инициализация
        ReloadContent();

        if (SaveLoadService.I.CanLoadSetups()) {
            SaveLoadService.I.LoadSetups();
        }

        if (DataControll.I.PlayerName == "") {
            DataControll.I.PlayerName = "DefaultName" + rand.nextInt(899) + 100;
        }

        SaveLoadService.I.SaveSetups();

        //инициализация страниц меню
        EngineService.I.Pages.put("LoadingScreen", new LoadingScreen());
        EngineService.I.Pages.put("MainMenu", new MainMenu());
        EngineService.I.Pages.put("GameMenu", new GameMenu());
        EngineService.I.Pages.put("SelectGameModePage", new SelectGameModePage());
        EngineService.I.Pages.put("AchievementsPage", new AchievementsPage());
        EngineService.I.Pages.put("LeaderbordPage", new LeaderbordPage());
        EngineService.I.Pages.put("ProfilePage", new ProfilePage());
        EngineService.I.Pages.put("LanguageSelectPage", new LanguageSelectPage());
        EngineService.I.Pages.put("TopLeaderbordPage", new TopLeaderbordPage());

        EngineService.I.NavigateTo("LoadingScreen", "", true);

        EngineService.I.Pages.get(EngineService.I.CurrentPageName).ContentUpdated();
    }

    //выгрузка ресурсов
    public void UnloadContent() {

    }

    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        //робота систем ядра
        //TouchService.I.Update(timeDelta);
        toRet = EngineService.I.Update(timeDelta);
        toRet = toRet | DebugService.I.Update(timeDelta);

        //оброботчик нажатия на кнопку назад
        if (!EngineService.I.OnWinPressed) {
            toRet = toRet | EngineService.I.Pages.get(EngineService.I.CurrentPageName).Update(timeDelta);
        }

        toRet = toRet | dialog.Update(timeDelta);

        return toRet;
    }

    public void Draw(SpriteBatch spriteBatch) {
        if (!seted) {
            spriteAdapter.spriteBatch = spriteBatch;
            seted = true;
        }
        //spriteAdapter.shapeRenderer = new ShapeRenderer();

        //вызов перезагрузки контента
        if (NeedToReLoad) {
            NeedToReLoad = false;
            ReloadContent();
        }

        //рисование конкретной страницы
        EngineService.I.Pages.get(EngineService.I.CurrentPageName).Draw(spriteAdapter);

        //PRODUCTION: убрать все нафиг
        /*
        TouchService.I.Draw(spriteAdapter);
        DebugService.I.Draw(spriteAdapter);
        */

        EngineService.I.Draw(spriteAdapter);

        //рисование черного екрана, имитируещего роботу телефона вне приложения
        if (EngineService.I.OnWinPressed) {
            spriteAdapter.FillRectangle(new Vector2(0), new Vector2(480, 800), Color.Black, 1, 0, 98);
            spriteAdapter.DrawText("Phone System", new Vector2(240, 300), Color.White, 1, "Arial32", 1, TextAlign.CenterXY, 0, 99);
            spriteAdapter.DrawText("press backspace", new Vector2(240, 350), Color.White, 1, "Arial20", 1, TextAlign.CenterXY, 0, 99);
        }

        dialog.Draw(spriteAdapter);

        Date date = new Date();
        spriteAdapter.FillRectangle(new Vector2(0, 0), new Vector2(DataControll.WindowWidth, 8), Color.Black, 0.3f, 0, 100);
        spriteAdapter.DrawText(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds(), new Vector2(DataControll.WindowWidth / 2, 4), Color.WhiteSmoke, 1, "Arial20", 0.3f, TextAlign.CenterXY, 0, 100);


        //PRODUCTION: убрать все нафиг
        /*
        spriteAdapter.FillRectangle(new Vector2(0, 720), new Vector2(480, 80), Color.LightGray, 1, 0, 60);
        */
    }
}

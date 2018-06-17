package pl.rozanski.view;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
    Font font = null;


    public FontLoader() {
        loadFont();
    }

    private void loadFont() {
        InputStream inputStream = this.getClass().getResourceAsStream("/fonts/PixelMplus10-Regular.ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public Font setFont(String purpose) {
        Font font = null;
        if ("score".equals(purpose)) {
            font = this.font.deriveFont(80f);
        } else if ("title".equals(purpose)) {
            font = this.font.deriveFont(180f);
        } else {
            font = this.font.deriveFont(80f);
        }
        return font;
    }

}

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Currency {
    
    public static void main(String[] args) throws Exception {
        Font quicksandFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/Quicksand.otf")).deriveFont(20f);
        UIManager.put("Label.font", quicksandFont);
        CurrencyFrame frame = new CurrencyFrame();
    }
}
 
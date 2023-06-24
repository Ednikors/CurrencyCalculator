import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Additional {
    public static ImageIcon getImage (String country_id) throws MalformedURLException, URISyntaxException, IOException{
        var urlstr = "https://flagsapi.com/"+country_id+"/flat/64.png";
        URL imageURL = new URI(urlstr).toURL();
        BufferedImage image = ImageIO.read(imageURL);
        Image resizedImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH); 
        return new ImageIcon(resizedImage);
    }


    public static String getCode(String[][] array, String country){
        String country_id = null;
        for (String[] innerArray : array) {
            if (innerArray[0].equals(country)) {
                country_id = innerArray[1];
            }
        }
        return country_id;
    }
    
    public static Double getExchangeRate(String fromCurr, String toCurr) throws MalformedURLException, JSONException, IOException, URISyntaxException{
        // Float exchangeRate = null;
        JSONObject jsonObject = new JSONObject(IOUtils.toString(
            new URI("https://v6.exchangerate-api.com/v6/93dff3a49bac40a73e6c0c5a/latest/"+fromCurr).toURL(), 
            Charset.forName("UTF-8"))).getJSONObject("conversion_rates");;
        Double exchangeRate = jsonObject.getDouble(toCurr);
        return exchangeRate;
    }
}

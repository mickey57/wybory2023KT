package pl.michal.wybory2023.app;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContactJSON {
    private List<NameValuePair> params = new ArrayList<>(2);
    private final String c_subject = "WYBORY2023";
    private final String c_rodo = "Wyrażam zgodę na przetwarzanie moich danych osobowych w zakresie wynikającym z prowadzenia działalności poselskiej oraz w celach informacyjnych i promocyjnych związanych z działalnością Ruchu Narodowego i Fundacji Piastowskiej, zgodnie z przepisami rozporządzenia Parlamentu Europejskiego i Rady UE 2016/679 z dnia 27.04.2016 r. w sprawie ochrony osób fizycznych w związku z przetwarzaniem danych osobowych oraz zgodnie z ustawą z dnia 10.05.2018 r. o ochronie danych osobowych (Dz. U. 2018.1000).";
    private final String c_kampania_id = "kt2023";
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public void createRequest(Contact contact) {
        params.add(new BasicNameValuePair("kampania_subject", c_subject));
        params.add(new BasicNameValuePair("contact_imie", contact.getFirstName()));
        params.add(new BasicNameValuePair("contact_nazwisko", contact.getLastName()));
        params.add(new BasicNameValuePair("contact_tel", contact.getPhone()));
        params.add(new BasicNameValuePair("contact_email", contact.getEmail()));
        params.add(new BasicNameValuePair("RODO", c_rodo));
        params.add(new BasicNameValuePair("kampania_id", c_kampania_id));
        params.add(new BasicNameValuePair("kampania_data", dtf.format(contact.getLdt())));
        params.add(new BasicNameValuePair("kampania_security_key", contact.getSecurityKey()));
        if(contact.getDistrict().equals(""))
            params.add(new BasicNameValuePair("contact_powiat", contact.getDistrict()));
        if(contact.getCommunity().equals(""))
            params.add(new BasicNameValuePair("contact_gmina", contact.getCommunity()));
        if(contact.getAddress().equals(""))
            params.add(new BasicNameValuePair("contact_adres", contact.getAddress()));
        if(contact.getBaner())
            params.add(new BasicNameValuePair("pomoc_baner", "1"));
        if(contact.getPlakat())
            params.add(new BasicNameValuePair("pomoc_plakat", "1"));
        if(contact.getUlotka())
            params.add(new BasicNameValuePair("pomoc_ulotka", "1"));
        if(contact.getWolontariat())
            params.add(new BasicNameValuePair("pomoc_wolontariat", "1"));
        if(contact.getPodpisy())
            params.add(new BasicNameValuePair("pomoc_podpisy", "1"));
        if(contact.getInnaPomoc())
            params.add(new BasicNameValuePair("pomoc_inna", "1"));
        if(contact.getKomisja())
            params.add(new BasicNameValuePair("pomoc_komisja", "1"));
        params.add(new BasicNameValuePair("contact_message", contact.getMessage()));
        params.add(new BasicNameValuePair("contact_ip", contact.getIpAdd()));
    }

    public void sendPOST() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("https://krzysztoftuduj.pl/api_kampania23.php");
            httpClient.execute(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.close();
        }
    }
}

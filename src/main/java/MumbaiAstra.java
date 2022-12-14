
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;

import java.nio.file.Paths;

public class MumbaiAstra {

    static final String ASTRA_ZIP_FILE = "/Users/pranjal/Documents/Astra-Project/MumbaiDatabasse-Bundle/secure-connect-mydb.zip";
    static final String ASTRA_USERNAME = "ytKEklPjxdFeOAQPufPJTmxl";
    static final String ASTRA_PASSWORD = "xYFu.TOofs82wtLoZbW82lGlsdXuoiA2D4ZgC,pX_iJB5nXpmB38ip47qsWwfNm81iQRJ,wrZZuCe1Oyd+LzNSuLL2t,3WzD4btpoZfZ+N0cslrmkAect4a5Mw,Nz-a_";
    static final String ASTRA_KEYSPACE = "myks";

    public static void main(String[] args) {
        // Logger logger = LoggerFactory.getLogger(MumbaiAstra.class);
        String city = "";
        String name = "";
        int id = 0;
        System.out.println("Initiating Connection to Astra Cluster");
        System.out.println(java.time.LocalDateTime.now());
        CqlSession cqlSession = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(ASTRA_ZIP_FILE))
                .withAuthCredentials(ASTRA_USERNAME, ASTRA_PASSWORD)
                .withKeyspace(ASTRA_KEYSPACE)
                .build();
        // logger.info("[OK] Welcome to ASTRA. Connected to Keyspace {}", cqlSession.getKeyspace().get());

        // logger.info("[OK] Success");
        // System.exit(0);

        System.out.println("Connected successfully to Astra Cluster");
        System.out.println(java.time.LocalDateTime.now());

        PreparedStatement ps1 = cqlSession.prepare("insert into myks.t1 (id,city,name) values (?,?,?)");
        PreparedStatement ps2 = cqlSession.prepare("insert into myks.t2 (id,city,name) values (?,?,?)");
        PreparedStatement ps3 = cqlSession.prepare("insert into myks.t3 (id,city,name) values (?,?,?)");

        for(int i = 0; i < 50000 ; i++ )
        {
            id = i;
            city = "this is city"+ i;
            name = "Pranjal - "+ i;
            cqlSession.execute(ps1.bind(id,city,name));
            cqlSession.execute(ps2.bind(id,city,name));
            cqlSession.execute(ps3.bind(id,city,name));
            if((i%1000) == 0)
            {
                System.out.println(java.time.LocalDateTime.now());
                System.out.println("Look, I have written " + String.valueOf(i) + " records by now!");
            }
        }

    }

}

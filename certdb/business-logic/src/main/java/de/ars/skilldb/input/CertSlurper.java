package de.ars.skilldb.input;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 * Liest die Zertifizierungen von einer gegebenen Webseite ein.
 *
 * Der {@code CertSlurper} dient dazu, die Zertifizierungen von der IBM-Webseite zu 'schlürfen' und
 * zurück zu geben. Die eingelesenen Werte werden als String-Literale zurückgegeben.
 *
 */
public class CertSlurper {

    private final String url;
    private final String tableClass;
    private Document htmlDocument;

    /**
     * Erzeugt einen neuen {@code CertSlurper}.
     *
     * @param url
     *            die URL der Webseite, von denen die Zertifizierungsdaten eingelesen werden sollen.
     * @param tableClass
     *            die CSS-Klasse der Tabelle, in der die Zertifizierungsdaten stehen.
     */
    public CertSlurper(final String url, final String tableClass) {
        this.url = url;
        this.tableClass = tableClass;
    }

    /**
     * Liest die Zertifizierungsdaten von der Webseite aus.
     *
     * @return eine Liste von {@code CertWebEntry}-Objekten, welche wiederum die String-Literale
     *         beinhalten.
     */
    public List<CertWebEntry> slurpCertWebEntries() {
        if (url.startsWith("http")) {
            connectToIbmCertPage();
        }
        else {
            connectToLocalFile();
        }

        Elements rows = htmlDocument.select("." + tableClass).select("tbody").select("tr");

        List<CertWebEntry> slurpedCerts = new LinkedList<CertWebEntry>();
        for (Element tr : rows) {
            CertWebEntry certToAdd = createCertificationFromTableRow(tr);
            slurpedCerts.add(certToAdd);
        }

        return slurpedCerts;
    }

    private void connectToIbmCertPage() {
        try {
            htmlDocument = Jsoup.connect(url).get();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Could not connect to the ibm eligible skills page.", e);
        }
    }

    private void connectToLocalFile() {
        String utf8 = "UTF-8";
        try {
            File input = new File(url);
            if (!input.exists()) {
                // Datei eventuell im Classpath
                InputStream inputStream = this.getClass().getResourceAsStream(url);

                if (inputStream == null) {
                    throw new FileNotFoundException("The file " + url + " could not be found.");
                }
                htmlDocument = Jsoup.parse(inputStream, utf8, "");
                return;
            }
            htmlDocument = Jsoup.parse(input, utf8);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Could not connect to local file: '" + url + "'.", e);
        }
    }

    private CertWebEntry createCertificationFromTableRow(final Element tr) {
        Elements tds = tr.getElementsByTag("td");

        CertWebEntry cert = new CertWebEntry();
        cert.setStatus(tds.get(0).text());
        cert.setSequenceNumber(tds.get(1).text());

        List<Node> nameNodes = tds.get(2).childNodes();

        String link = nameNodes.get(0).attr("abs:href");
        if (!"".equals(link)) {
            cert.setLink(link);
        }
        cert.setName(tds.get(2).text());

        cert.setTechnical(tds.get(3).text());
        cert.setSkillPoint(tds.get(4).text());
        cert.setExpirationDate(tds.get(5).text());
        cert.setCertificationCode(tds.get(6).text());
        cert.setVersion(tds.get(7).text());
        cert.setBrand(tds.get(8).text());
        cert.setProductGroup(tds.get(9).text());

        return cert;
    }

}

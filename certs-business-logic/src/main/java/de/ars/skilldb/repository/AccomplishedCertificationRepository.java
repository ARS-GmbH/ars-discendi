/**
 *
 */
package de.ars.skilldb.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.*;

/**
 * Spring-Data Repository für die Klasse {@code AccomplishedCertification}.
 */
public interface AccomplishedCertificationRepository extends
        PagingAndSortingRepository<AccomplishedCertification, Long> {

    /**
     * Findet alle abgeschlossenen Zertifizierungen eines Benutzers.
     * 
     * @param user
     *            der Benutzer, zu dem alle abgeschlossenen Zertifizierungen gefunden werden sollen.
     * @return alle abgeschlossenen Zertifizierungen eines Benutzers.
     */
    Iterable<AccomplishedCertification> findByUser(User user);

    /**
     * Findet alle abgeschlossenen Zertifizierungen zu einer Zertifizierung.
     * 
     * @param certification
     *            die Zertifizierung, zu der alle abgeschlossenen Zertifizierungen gefunden werden
     *            sollen.
     * @return alle abgeschlossenen Zertifizierungen einer Zertifizierung.
     */
    Iterable<AccomplishedCertification> findByCertification(Certification certification);

    /**
     * Findet alle abgeschlossenen Zertifizierungen zu einem Benutzer, die in Kürze ablaufen.
     * 
     * @param user
     *            der Benutzer, zu dem die in Kürze ablaufenden, abgeschlossenen Zertifizierungen
     *            gefunden werden sollen.
     * @param compareDate
     *            das Vergleichsdatum im {@code long}-Format als {@code String}.
     * @return alle in Kürze ablaufendne, abgeschlossenen Zertifizierungen des spezifizierten
     *         Benutzers.
     */
    @Query("Start user=node({0}) Match (user)-[:OWNS]->(a:AccomplishedCertification)-[:ORIGINATES_FROM]->(c:Certification)"
            + "Where c.expirationDate >= {1} Return a")
    Iterable<AccomplishedCertification> findByUserExpiringSoon(User user, String compareDate);

    /**
     * Findet alle abgeschlossenen Zertifizierungen, die in einer bestimmten Produktgruppe abgelegt
     * wurden und einen bestimmten Typ (Sales oder Technical) haben.
     * 
     * @param productGroup
     *            die {@code ProductGroup}, für die die gesuchte Zertifizierung gültig sein soll.
     * @param certKind
     *            der Typ der Zertifizierung (Sales, Technical)
     * @return alle abgeschlossenen Zertifizierungen der Produktgruppe mit angegebenem Typ.
     */
    @Query("Start productGroup=node({0}) "
            + "Match (productGroup)-[:RELEVANT_FOR]-(c:Certification {kind:{1}})-[:ORIGINATES_FROM]-(ac:AccomplishedCertification) "
            + "return ac")
    Iterable<AccomplishedCertification> findByProductGroupAndKind(ProductGroup productGroup, String certKind);

    /**
     * Liefert {@code true} zurück, wenn der Benutzer alle Tests bestanden hat, die für den
     * spezifizierten Pfad notwendig sind.
     * 
     * @param path
     *            der Zertifizierungspfad, auf den hin überprüft werden soll.
     * @param user
     *            der Benutzer, für den überprüft wird, ob alle notwendigen Tests abgelegt wurden.
     * @return {@code true} wenn der Benutzer alle notwendigen Tests abgelegt hat, andernfalls
     *         {@code false}.
     */
    @Query("Start path=node({0}), user=node({1}) match (path)<-[:NECESSARY_EXAM]-(necexam) "
            + "optional match (user)-[s:ACCOMPLISHED]->(userexam)-[:NECESSARY_EXAM]-(path) "
            + "with count(distinct necexam) as cnt_necexam, count(distinct userexam) as cnt_userexam "
            + "return cnt_necexam = 0 OR cnt_userexam = cnt_necexam;")
    boolean hasUserNecessaryExamsForPath(Path path, User user);

    /**
     * Liefert {@code true} zurück, wenn der Benutzer einen der Tests bestanden hat, die für den
     * spezifizierten Pfad wählbar sind.
     * 
     * @param path
     *            der Zertifizierungspfad, auf den hin überprüft werden soll.
     * @param user
     *            der Benutzer, für den überprüft wird, ob einer der wählbaren Tests abgelegt wurde.
     * @return {@code true} wenn der Benutzer einen wählbaren Test abgelegt hat, andernfalls
     *         {@code false}.
     */
    @Query("Start path=node({0}), user=node({1}) " + "match (path)<-[:CHOOSABLE_EXAM]-(choexam) "
            + "optional match (user)-[s:ACCOMPLISHED]->(userexam)-[:CHOOSABLE_EXAM]-(path) "
            + "with count(distinct choexam) as cnt_choexam, count(distinct userexam) as cnt_userexam "
            + "return cnt_choexam = 0 OR cnt_userexam >= 1;")
    boolean hasUserOneChoosableExamForPath(Path path, User user);

    /**
     * Liefert {@code true} zurück, wenn der Benutzer über alle Zertifizierungen verfügt, die für
     * den spezifizierten Pfad notwendig sind.
     * 
     * @param path
     *            der Zertifizierungspfad, auf den hin überprüft werden soll.
     * @param user
     *            der Benutzer, für den überprüft wird, ob er alle benötigten Zertifizierungen
     *            besitzt.
     * @return {@code true} wenn der Benutzer die Zertifizierungen besitzt, andernfalls
     *         {@code false}.
     */
    @Query("Start path=node({0}), user=node({1})"
            + "match (path)-[:NECESSARY_CERTIFICATION]-(neccert)"
            + "match (user)-[:OWNS]->(a:AccomplishedCertification)-[:ORIGINATES_FROM]->(acccert)-[:NECESSARY_CERTIFICATION]-(path)"
            + "return count(distinct neccert) = count(distinct acccert);")
    boolean hasUserNecessaryCertificationsForPath(Path path, User user);

}

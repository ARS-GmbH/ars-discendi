/**
 *
 */
package de.ars.skilldb.view.model;

import java.util.HashMap;
import java.util.Map;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.KnowledgeCategory;

/**
 * Repräsentiert ein Hilfsobjekt für die Darstellung von Wissensgebieten und
 * Kategorien in einem Baum.
 */
public class KnowledgeNode {
    private String id;
    private String text;
    private String icon;
    private Map<String, String> a_attr = new HashMap<String, String>();
    private boolean children;

    /**
     * Erzeugt einen neuen Kategorie-Knoten im Baum.
     *
     * @param categorie
     *            Kategorie, die im Baum dargestellt werden soll
     */
    public KnowledgeNode(final KnowledgeCategory categorie) {
        super();
        id = "" + categorie.getId();
        text = "" + categorie.getName();
        a_attr.put("href", "categories/" + categorie.getId());

        if (!categorie.getChildrenCategories().isEmpty() || !categorie.getChildrenKnowledges().isEmpty()) {
            children = true;
        }
    }

    /**
     * Erzeugt einen neuen Wissensgebiets-Knoten im Baum.
     *
     * @param knowledge
     *            Wissensgebiet, das im Baum dargestellt werden soll
     */
    public KnowledgeNode(final Knowledge knowledge) {
        super();
        id = "" + knowledge.getId();
        text = "" + knowledge.getName();
        icon = "glyphicon glyphicon-book";
        a_attr.put("href", "" + knowledge.getId());
    }

    /**
     * Erzeugt einen leeren Knoten im Baum.
     */
    public KnowledgeNode() {
       super();
    }

    public String getId() {
        return id;
    }
    public void setId(final String id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(final String text) {
        this.text = text;
    }

    public boolean isChildren() {
        return children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public Map<String, String> getA_attr() {
        return a_attr;
    }

    public void setA_attr(final Map<String, String> a_attr) {
        this.a_attr = a_attr;
    }

}


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class JourFerie {

    private int id;
    private Date date;
    private String libelle;

    public JourFerie() {
    }

    public JourFerie(int id, Date date, String libelle) {
        this.id = id;
        this.date = date;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void ajouter(Connection connection) throws SQLException {
        if (date == null) {
            throw new IllegalArgumentException("La date du jour férié est obligatoire.");
        }
        if (libelle == null || libelle.trim().isEmpty()) {
            throw new IllegalArgumentException("Le libellé du jour férié est obligatoire.");
        }

        String sql = "INSERT INTO jour_ferie (date, libelle) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(date.getTime()));
        stmt.setString(2, libelle);
        stmt.executeUpdate();
        stmt.close();
    }

    public void modifier(Connection connection) throws SQLException {
        if (id <= 0) {
            throw new IllegalStateException("Impossible de modifier un jour férié sans identifiant valide.");
        }
        if (date == null) {
            throw new IllegalArgumentException("La date du jour férié est obligatoire.");
        }
        if (libelle == null || libelle.trim().isEmpty()) {
            throw new IllegalArgumentException("Le libellé du jour férié est obligatoire.");
        }

        String sql = "UPDATE jour_ferie SET date = ?, libelle = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(date.getTime()));
        stmt.setString(2, libelle);
        stmt.setInt(3, id);
        stmt.executeUpdate();
        stmt.close();
    }

    public void supprimer(Connection connection) throws SQLException {
        if (id <= 0) {
            throw new IllegalStateException("Impossible de supprimer un jour férié sans identifiant valide.");
        }

        String sql = "DELETE FROM jour_ferie WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public String toString() {
        return "JourFerie{id=" + id + ", date=" + date + ", libelle='" + libelle + "'}";
    }
}

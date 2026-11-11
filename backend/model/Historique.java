import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Historique {
    private static List<Historique> ListeHist=new ArrayList<>();
    private int id;
    private String action;
    private Date dateAction;
    private String description;

    public Historique(int id,String action,Date dateAction,String description){
        this.id=id;
        this.action=action;
        this.dateAction=dateAction;
        this.description=description;
        
    }
    public void enregistrerAction(int id,String action,Date dateAction,String description){
        ListeHist.add(new Historique(id, action, dateAction, description));
    }
    public List<Historique> consulterHistorique(){
        return ListeHist;
        
    }
    public List<Historique> consulterHistoriqueParPersonne(){
        return ListeHist;
        
    }

}

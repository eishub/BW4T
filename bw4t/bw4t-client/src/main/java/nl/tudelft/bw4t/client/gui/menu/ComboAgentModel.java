package nl.tudelft.bw4t.client.gui.menu;

import java.util.Collection;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import eis.exceptions.EntityException;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.network.BW4TServerHiddenActions;
import nl.tudelft.bw4t.network.BW4TServerActions;
import nl.tudelft.bw4t.server.BW4TServer;

public class ComboAgentModel extends AbstractListModel implements ComboBoxModel {

    private final BW4TClientGUI gui;
    //private final BW4TServer server;
    private String selection = "";

    public ComboAgentModel(BW4TClientGUI clientGUI/*, BW4TServer server*/) {
        this.gui = clientGUI;
        //this.server = server;
    }

    @Override
    public Object getElementAt(int arg0) {
   /*     List<String> bots = new ArrayList<String>();
        Collection<String> entities = server.getEntities();
        for(Iterator<String> iterator = entities.iterator(); iterator.hasNext();) {
            String entity = iterator.next();
            
            if(getType(entity).toString().equals("Epartner")) {
                continue;
            } else {
                bots = bots.add(entity);
            }
        }
        
        return bots.get(arg0);*/
        try {
            if(gui.environment.getType(gui.environment.getAgents().get(arg0)).equals("epartner")) {
                return null;
            } else {
                return gui.environment.getAgents().get(arg0);
            }
        } catch (EntityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
        
        //return gui.environment.getAgents().get(arg0);
    }

    @Override
    public int getSize() {
        return gui.environment.getAgents().size();
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public void setSelectedItem(Object arg0) {
        selection = (String) arg0;
    }

}

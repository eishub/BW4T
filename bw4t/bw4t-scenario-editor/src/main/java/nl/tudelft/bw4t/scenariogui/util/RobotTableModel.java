package nl.tudelft.bw4t.scenariogui.util;

import java.io.Serializable;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;

/**
 * This class creates a table which can hold bots.
 */
public class RobotTableModel extends AbstractTableModel implements Serializable {

    private static final long serialVersionUID = 4899095629026343945L;

    /**
     * Constructor.
     */
    public RobotTableModel() {
        super();
    }

    @Override
    public Class<?> getColumnClass(int col) {
        if (col == 3) {
            return Integer.class;
        }
        return super.getColumnClass(col);
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int col) {
        switch (col) {
        case 0:
            return "Bot";
        case 1:
            return "Controller";
        case 2:
            return EntityPanel.AGENT_FILE;
        case 3:
        default:
            return EntityPanel.NUMBER_BOTS_COLUMN;
        }
    }

    @Override
    public int getRowCount() {
        if (getConfig() == null) {
            return 0;
        }
        return getConfig().getBots().size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (getConfig() == null) {
            return "";
        }
        BotConfig bot = getConfig().getBot(row);
        switch (col) {
        case 0:
            return bot.getBotName();
        case 1:
            return bot.getBotController().toString();
        case 2:
            return bot.getFileName();
        case 3:
            return bot.getBotAmount();
        default:
            return "";
        }
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
        if (getConfig() == null) {
            return false;
        }
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (getConfig() == null) {
            return;
        }
        BotConfig bot = getConfig().getBot(row);
        switch (col) {
        case 0:
            bot.setBotName((String) value);
            break;
        case 1:
            if (value instanceof EntityType) {
                bot.setBotController((EntityType) value);
            } else if (value instanceof String) {
                String cont = (String) value;
                cont = cont.toLowerCase();
                if (EntityType.AGENT.nameLower().equals(cont)) {
                    bot.setBotController(EntityType.AGENT);
                } else if (EntityType.HUMAN.nameLower().equals(cont)) {
                    bot.setBotController(EntityType.HUMAN);
                }
            }
            break;
        case 2:
            bot.setFileName((String) value);
            break;
        case 3:
            if(value instanceof String) {
            bot.setBotAmount(Integer.parseInt((String) value));
            } else if (value instanceof Integer) {
                bot.setBotAmount((Integer) value);
            }
            break;
        default:
        }
    }

}

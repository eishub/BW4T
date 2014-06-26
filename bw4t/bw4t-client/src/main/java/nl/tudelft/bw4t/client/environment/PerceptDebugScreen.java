package nl.tudelft.bw4t.client.environment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import nl.tudelft.bw4t.client.gui.menu.ComboEntityModel;
import nl.tudelft.bw4t.client.gui.menu.EntityComboModelProvider;
import eis.iilang.Percept;

public class PerceptDebugScreen extends JFrame implements Runnable, ActionListener, EntityComboModelProvider {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private RemoteEnvironment env;

    private DefaultListModel<String> perceptModel = new DefaultListModel<>();
    private JList<String> perceptList = new JList<>(perceptModel);
    private JComboBox<String> entityCombo;
    private PerceptDebugScreen that = this;

    public PerceptDebugScreen(RemoteEnvironment env) {
        this.env = env;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        entityCombo = new JComboBox<String>(new ComboEntityModel(this));

        add(entityCombo, BorderLayout.NORTH);
        add(new JScrollPane(perceptList), BorderLayout.CENTER);

        entityCombo.addActionListener(this);

        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    SwingUtilities.invokeAndWait(that);
                } catch (InvocationTargetException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 0, 200, TimeUnit.MILLISECONDS);

        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(150, 300));
        setSize(getPreferredSize());
        setVisible(true);
    }

    public void run() {
        String selectedEntity = (String) entityCombo.getSelectedItem();

        perceptModel.clear();
        final List<Percept> storedPercepts = env.getStoredPercepts(selectedEntity);
        if (storedPercepts != null) {
            for (Percept p : storedPercepts) {
                perceptModel.addElement(p.toProlog());
            }
        }
        
        revalidate();
        repaint();
    }

    @Override
    public void dispose() {
        scheduler.shutdown();
        super.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.run();
    }

    @Override
    public Collection<String> getEntities() {
        try {
            return env.getEntities();
        } catch (NullPointerException e) {
            return null;
        }
    }
}

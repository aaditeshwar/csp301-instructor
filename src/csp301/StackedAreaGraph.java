package csp301;

import javax.swing.JFrame;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.StackedAreaChart;
import prefuse.data.Table;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.PolygonRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

class StackedAreaGraph {
    public static void main(String[] args) {
        ActionList color = new ActionList();
        int[] palette = new int[] {
            ColorLib.rgba(255,200,200,150),
            ColorLib.rgba(200,255,200,150)
        };
        ColorAction fillColor = new DataColorAction("table", "name",
                Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
        color.add(fillColor);

        ActionList layout = new ActionList();
        layout.add(new RepaintAction());
        String[] fields = { "1980s", "1990s", "2000s" };
        layout.add(new StackedAreaChart("table", VisualItem.POLYGON, fields));

        Visualization vis = new Visualization();
        Table table = new Table();
        vis.add("table", table);

        table.addColumn("name", String.class);
        table.addColumn("1980s", int.class);
        table.addColumn("1990s", int.class);
        table.addColumn("2000s", int.class);
        table.addColumn(VisualItem.POLYGON, float[].class, null);
        table.addColumn(VisualItem.POLYGON+":start", float[].class, null);
        table.addColumn(VisualItem.POLYGON+":end", float[].class, null);

        int rowNumber = table.addRow();
        table.setString(rowNumber, "name", "Bob");
        table.setInt(rowNumber, "1980s", 1000);
        table.setInt(rowNumber, "1990s", 500);
        table.setInt(rowNumber, "2000s", 300);

        rowNumber = table.addRow();
        table.setString(rowNumber, "name", "Mary");
        table.setInt(rowNumber, "1980s", 800);
        table.setInt(rowNumber, "1990s", 1500);
        table.setInt(rowNumber, "2000s", 3200);

        vis.putAction("layout", layout);
        vis.putAction("color", color);

        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.add("ingroup('table')", new PolygonRenderer());
        vis.setRendererFactory(drf);

        Display display = new Display(vis);
        display.setSize(720, 500);

        JFrame frame = new JFrame("Prefuse StackedAreaChart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(display);
        frame.pack();
        frame.setVisible(true);

        vis.run("layout");
        vis.run("color");
    }
}

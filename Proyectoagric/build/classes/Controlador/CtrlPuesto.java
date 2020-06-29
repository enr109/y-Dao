package Controlador;
import Modelo.DAO.DaoPuestos;
import Modelo.VO.VoPuestos;
import Vista.Puestos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kike6
 */
public class CtrlPuesto implements ActionListener{
    private VoPuestos Vopues;
    private DaoPuestos Dapues;
    private Puestos frm;
    
    public CtrlPuesto(VoPuestos Vopues,DaoPuestos Dapues,Puestos frm){
        this.Vopues=Vopues;
        this.Dapues=Dapues;
        this.frm=frm;
        this.frm.btnregistrar.addActionListener(this);
        this.frm.btnactualizar.addActionListener(this);
        this.frm.btneliminar.addActionListener(this);
        llenarGrid();
    }
    
    public void iniciar(){
        frm.setTitle("Puestos");
        frm.setLocationRelativeTo(null);
        llenarGrid();
    }
    
    private void llenarGrid(){
        ResultSet rs = Dapues.getConsulta();
        DefaultTableModel modelo=(DefaultTableModel)frm.tabla.getModel();
        frm.tabla.getSelectedRow();
        
        while(modelo.getRowCount()>0) modelo.removeRow(0);
        String Id,Puesto;
        try{
            while(rs.next()){
                Id=rs.getString("idpuesto");
                Puesto=rs.getString("vchpuesto");
                
                modelo.addRow(new Object []{Id,Puesto});
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public void limpiar(){
        frm.txtpuesto.setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== frm.btnregistrar){
             Vopues.setNompuesto(frm.txtpuesto.getText());
             if(Dapues.registrar(Vopues)){
                 JOptionPane.showMessageDialog(null, "Registro Guardado");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Guardar");
             }
         }
        
        if(e.getSource()== frm.btnactualizar){
            int fila = frm.tabla.getSelectedRow();
             Vopues.setId(Integer.parseInt(frm.tabla.getValueAt(fila, 0).toString()));
             
             Vopues.setNompuesto(frm.txtpuesto.getText());
             
             if(Dapues.modificar(Vopues)){
                 JOptionPane.showMessageDialog(null, "Registro Modificar");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Modificar");
             }
         }
        
        if(e.getSource()== frm.btneliminar){
            int fila = frm.tabla.getSelectedRow();
             Vopues.setId(Integer.parseInt(frm.tabla.getValueAt(fila, 0).toString()));
             
             //mod.setPrecio(Float.parseFloat(frm.txtprecio.getText()));
             
             if(Dapues.eliminar(Vopues)){
                 JOptionPane.showMessageDialog(null, "Registro Eliminar");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Eliminar");
             }
         }
        
        
        
    }
    
    
}

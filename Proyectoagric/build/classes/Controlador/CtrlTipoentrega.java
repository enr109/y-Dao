package Controlador;

import Modelo.DAO.DaoTipoentrega;
import Modelo.VO.VoTipoentrega;
import Vista.Tipoentregas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Enrique
 */
public class CtrlTipoentrega implements ActionListener {
    private VoTipoentrega  Voti;
    private DaoTipoentrega Daoti;
    private Tipoentregas frm;
    
    public  CtrlTipoentrega(VoTipoentrega  Voti,DaoTipoentrega Daoti,Tipoentregas frm){
        this.Voti=Voti;
        this.Daoti=Daoti;
        this.frm=frm;
        this.frm.btnagregar.addActionListener(this);
        this.frm.btnactualizar.addActionListener(this);
        this.frm.btneliminar.addActionListener(this);
    }
    
    public void iniciar(){
        frm.setTitle("Tipo de entregas");
        frm.setLocationRelativeTo(null);
        llenarGrid();
    }
    
    private void llenarGrid(){
        ResultSet rs = Daoti.getConsulta();
        DefaultTableModel modelo=(DefaultTableModel)frm.tabla.getModel();
        frm.tabla.getSelectedRow();
        
        while(modelo.getRowCount()>0) modelo.removeRow(0);
        String Id,tipo;
        try{
            while(rs.next()){
                Id=rs.getString("idtientrega");
                tipo=rs.getString("vchentrega");
                
                modelo.addRow(new Object []{Id,tipo});
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public void limpiar(){
        frm.txttipo.setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== frm.btnagregar){
             Voti.setTipoen(frm.txttipo.getText());
             if(Daoti.registrar(Voti)){
                 JOptionPane.showMessageDialog(null, "Registro Guardado");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Guardar");
             }
         }
        
        if(e.getSource()== frm.btnactualizar){
            int fila = frm.tabla.getSelectedRow();
             Voti.setId(Integer.parseInt(frm.tabla.getValueAt(fila, 0).toString()));
             
             Voti.setTipoen(frm.txttipo.getText());
             
             if(Daoti.modificar(Voti)){
                 JOptionPane.showMessageDialog(null, "Registro Modificar");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Modificar");
             }
         }
        
        
        if(e.getSource()== frm.btneliminar){
            int fila = frm.tabla.getSelectedRow();
             Voti.setId(Integer.parseInt(frm.tabla.getValueAt(fila, 0).toString()));
             
             //mod.setPrecio(Float.parseFloat(frm.txtprecio.getText()));
             
             if(Daoti.eliminar(Voti)){
                 JOptionPane.showMessageDialog(null, "Registro Eliminar");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Eliminar");
             }
         }
    }
    
}

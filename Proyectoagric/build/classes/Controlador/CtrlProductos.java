/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Modelo.DAO.DaoProductos;

import Modelo.VO.VoProductos;

import Vista.Productos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Enrique
 */
public class CtrlProductos implements ActionListener{
    private VoProductos Vope;
    private DaoProductos Daope;
    private Productos frm;
    
    public CtrlProductos(VoProductos Vope,DaoProductos Daope,Productos frm){
       this.Vope=Vope;
       this.Daope=Daope;
       this.frm=frm;
       this.frm.btnagregar.addActionListener(this);
       this.frm.btnactualizar.addActionListener(this);
       this.frm.btneliminar.addActionListener(this);
       
    }
    
    public void iniciar(){
        frm.setTitle("Productos");
        frm.setLocationRelativeTo(null);
        llenarGrid();
    }
    
    private void llenarGrid(){
        ResultSet rs = Daope.getConsulta();
        DefaultTableModel modelo=(DefaultTableModel)frm.tabla.getModel();
        frm.tabla.getSelectedRow();
        
        while(modelo.getRowCount()>0) modelo.removeRow(0);
        String Id,nomp,pre,cost;
        try{
            while(rs.next()){
                Id=rs.getString("idproductos");
                nomp=rs.getString("vchnomproduct");
                pre=rs.getString("Proprecio");
                cost=rs.getString("Procosto");
                
                modelo.addRow(new Object []{Id,nomp,pre,cost});
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public void limpiar(){
        frm.txtproducto.setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== frm.btnagregar){
             Vope.setNompro(frm.txtproducto.getText());
             Vope.setPrecio(Float.parseFloat(frm.txtprecio.getText()));
             Vope.setCosto(Float.parseFloat(frm.txtcosto.getText()));
             if(Daope.registrar(Vope)){
                 JOptionPane.showMessageDialog(null, "Registro Guardado");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Guardar");
             }
         }
        
        if(e.getSource()== frm.btnactualizar){
            int fila = frm.tabla.getSelectedRow();
             Vope.setId(Integer.parseInt(frm.tabla.getValueAt(fila, 0).toString()));
             
             Vope.setNompro(frm.txtproducto.getText());
             Vope.setPrecio(Float.parseFloat(frm.txtprecio.getText()));
             Vope.setCosto(Float.parseFloat(frm.txtcosto.getText()));
             
             if(Daope.modificar(Vope)){
                 JOptionPane.showMessageDialog(null, "Registro Modificar");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Modificar");
             }
         }
        
        
        if(e.getSource()== frm.btneliminar){
            int fila = frm.tabla.getSelectedRow();
             Vope.setId(Integer.parseInt(frm.tabla.getValueAt(fila, 0).toString()));
             
             //mod.setPrecio(Float.parseFloat(frm.txtprecio.getText()));
             
             if(Daope.eliminar(Vope)){
                 JOptionPane.showMessageDialog(null, "Registro Eliminar");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Eliminar");
             }
         }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.DAO.DaoSucursal;
import Modelo.VO.VoSucursal;
import Vista.Sucursal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Enrique
 */
public class CtrlSucursal implements ActionListener{
    private VoSucursal Vosu;
    private DaoSucursal Daosu;
    private Sucursal frm;
    
    public CtrlSucursal(VoSucursal Vosu,DaoSucursal Daosu,Sucursal frm){
        this.Vosu=Vosu;
        this.Daosu=Daosu;
        this.frm=frm;
        this.frm.btnagregar.addActionListener(this);
        this.frm.btnactualizar.addActionListener(this);
        this.frm.btneliminar.addActionListener(this);
    }
    
    public void iniciar(){
        frm.setTitle("Sucursales");
        frm.setLocationRelativeTo(null);
        llenarGrid();
    }
    
    private void llenarGrid(){
        ResultSet rs = Daosu.getConsulta();
        DefaultTableModel modelo=(DefaultTableModel)frm.tabla.getModel();
        frm.tabla.getSelectedRow();
        
        while(modelo.getRowCount()>0) modelo.removeRow(0);
        String Id,nomsuc,direccion;
        try{
            while(rs.next()){
                Id=rs.getString("idsucursal");
                nomsuc=rs.getString("vchnomsucursal");
                direccion=rs.getString("vchsucdireccion");
                
                modelo.addRow(new Object []{Id,nomsuc,direccion});
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    public void limpiar(){
        frm.txtnombre.setText(null);
        frm.txtdireccion.setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== frm.btnagregar){
             Vosu.setNombsu(frm.txtnombre.getText());
             Vosu.setDireccion(frm.txtdireccion.getText());
             if(Daosu.registrar(Vosu)){
                 JOptionPane.showMessageDialog(null, "Registro Guardado");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Guardar");
             }
         }
        
        if(e.getSource()== frm.btnactualizar){
            int fila = frm.tabla.getSelectedRow();
             Vosu.setId(Integer.parseInt(frm.tabla.getValueAt(fila, 0).toString()));
             
             Vosu.setNombsu(frm.txtnombre.getText());
             Vosu.setDireccion(frm.txtdireccion.getText());
             
             if(Daosu.modificar(Vosu)){
                 JOptionPane.showMessageDialog(null, "Registro Modificar");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Modificar");
             }
         }
        
        
        if(e.getSource()== frm.btneliminar){
            int fila = frm.tabla.getSelectedRow();
             Vosu.setId(Integer.parseInt(frm.tabla.getValueAt(fila, 0).toString()));
             
             //mod.setPrecio(Float.parseFloat(frm.txtprecio.getText()));
             
             if(Daosu.eliminar(Vosu)){
                 JOptionPane.showMessageDialog(null, "Registro Eliminar");
                 llenarGrid();
                 limpiar();
             }else{
                 JOptionPane.showMessageDialog(null, "Error al Eliminar");
             }
         }
    }
    
    
    
}

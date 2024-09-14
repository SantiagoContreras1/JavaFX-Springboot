package com.santiagocontreras.webapp.biblioteca.service;

import java.util.List;

import com.santiagocontreras.webapp.biblioteca.model.Cliente;

public interface IClienteService {

    public List<Cliente> listarClieantes();

    public Cliente buscarClientePorId(Long dpi);

    public Cliente guardarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);
}

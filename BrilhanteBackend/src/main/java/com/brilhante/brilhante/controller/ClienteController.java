package com.brilhante.brilhante.controller;

import com.brilhante.brilhante.entity.Cliente;
import com.brilhante.brilhante.entity.Login;
import com.brilhante.brilhante.entity.MsgRetorno;
import com.brilhante.brilhante.service.ClienteService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    @Autowired
    ClienteService clienteservice;
    //GET  http://localhost:8010/apiweb/cliente/12

    @CrossOrigin(origins = "*")
    @GetMapping("/cliente")
    public ResponseEntity<Object> listarClientes() {
        List<Cliente> clientes = clienteservice.listarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cliente/{id}")
    public ResponseEntity<Object> consultaCliente(@PathVariable(value = "id") Long idCliente) {

        Optional<Cliente> cliente = clienteservice.consultarCliente(idCliente);
        if (cliente.isPresent()) {
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        } else {
            MsgRetorno erro = new MsgRetorno();
            erro.setFuncao("Consultar Cliente");
            erro.setDescrição("Erro ao consultar Cliente ID: " + idCliente);
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/cliente")
    public ResponseEntity<Object> incluirCliente(@RequestBody Cliente cliente) {

        Long idCli = clienteservice.incluirCliente(cliente);
        if (idCli != null && idCli > 0) {
            return new ResponseEntity<>(idCli, HttpStatus.OK);
        } else {
            MsgRetorno erro = new MsgRetorno();
            erro.setFuncao("Incluir Cliente");
            erro.setDescrição("Erro ao incluir Cliente! Chame a TI!!");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/cliente")
    public ResponseEntity atualizarCliente(@RequestBody Cliente cliente) {
        MsgRetorno msg = new MsgRetorno();
        msg.setFuncao("Atualizar Cliente");
        if (clienteservice.atualizarCliente(cliente)) {
            msg.setDescrição("Cliente ID " + cliente.getIDCliente() + " atualizado com sucesso!");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            msg.setDescrição("Erro ao atualizar cliente ID " + cliente.getIDCliente());
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity deletarCliente(@PathVariable(value = "id") Long id) {
        MsgRetorno msg = new MsgRetorno();
        msg.setFuncao("Excluir Cliente");
        if (clienteservice.excluirCliente(id)) {
            msg.setDescrição("Cliente ID " + id + " excluído com sucesso!");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            msg.setDescrição("Erro ao excluir cliente ID " + id + ", não cadastrado/inexistente!");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/cliente/login")
    public ResponseEntity<Object> loginCliente(@Valid @RequestBody Login login) {

        Cliente cli = clienteservice.loginCliente(login.getEmail(), login.getSenha());
         if(cli != null){
            return new ResponseEntity<>(cli, HttpStatus.OK);
        } else {
            MsgRetorno erro = new MsgRetorno();
            erro.setFuncao("Login Cliente");
            erro.setDescrição("Erro no Login ou senha inválidos/inexistentes!");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);            
        }
    }
}

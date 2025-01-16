package br.com.henriquenunes.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Metodos de acesso do HTTP:
 * GET - Buscar uma informação
 * POST - Adicionar um dado/informação
 * PUT - Alterar um dado/info
 * DELETE - Remover um dado
 * PATCH - Alterar somente uma parte da info/dado
 */
@RestController
@RequestMapping("/primeiraRota")

public class Controller {
    @GetMapping("/")
    public String mensagem(){
        return "Funcionou";
    }
}

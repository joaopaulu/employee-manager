package tech.getarrays.employeemanager.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.getarrays.employeemanager.exception.EntidadeNaoEncontradaException;
import tech.getarrays.employeemanager.exception.NegocioException;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.repository.EmployeeRepository;
import tech.getarrays.employeemanager.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> listar() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{employeeId}")
    public Employee buscar(@PathVariable Long employeeId) {
        return employeeService.buscarOuFalhar(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee adicionar(@RequestBody Employee employee) {
        try {
            return employeeService.salvar(employee);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{employeeId}")
    public Employee atualizar(@PathVariable Long employeeId,
                            @RequestBody Employee employee) {
        Employee employeeAtual = employeeService.buscarOuFalhar(employeeId);

        BeanUtils.copyProperties(employee, employeeAtual, "id");

        try {
            return employeeService.salvar(employeeAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long employeeId) {
        employeeService.excluir(employeeId);
    }
}

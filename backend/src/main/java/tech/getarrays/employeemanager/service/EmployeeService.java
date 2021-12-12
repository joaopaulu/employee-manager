package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import tech.getarrays.employeemanager.exception.EntidadeEmUsoException;
import tech.getarrays.employeemanager.exception.EntidadeNaoEncontradaException;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private String MSG_EMPLOYEE_NOT_FOND
            = "Não existe um cadastro de employee com o código %d";

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee salvar(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void excluir(Long employeeId) {
        try {
            employeeRepository.deleteById(employeeId);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_EMPLOYEE_NOT_FOND, employeeId));
        }
    }

    public Employee buscarOuFalhar(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_EMPLOYEE_NOT_FOND, employeeId)));
    }
}

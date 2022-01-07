package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.getarrays.employeemanager.exception.EntidadeNaoEncontradaException;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.repository.EmployeeRepository;

import java.util.UUID;

@Service
@Transactional
public class EmployeeService {

    private final String MSG_EMPLOYEE_NOT_FOND
            = "Não existe um cadastro de employee com o código %d";

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee salvar(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepository.save(employee);
    }

    public Employee atualizar(Employee employee) {
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

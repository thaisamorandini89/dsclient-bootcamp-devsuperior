package com.devsuperior.dsclient.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsclient.dto.ClientDTO;
import com.devsuperior.dsclient.entities.Client;
import com.devsuperior.dsclient.repositories.ClientRepository;
import com.devsuperior.dsclient.services.exceptions.ResourceEntityNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	/* Por enquanto esta listando todos os clientes */
	@Transactional(readOnly = true)
	public List<ClientDTO> findAll() {
		List<Client> list = repository.findAll();
		return list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceEntityNotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyEntityToDto(entity, dto);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
			copyEntityToDto(entity, dto);
			entity = repository.save(entity);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			// TODO: handle exception
			throw new ResourceEntityNotFoundException("Id not found " + id);
		}

	}

	private void copyEntityToDto(Client entity, ClientDTO dto) {
		// TODO Auto-generated method stub
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}

}

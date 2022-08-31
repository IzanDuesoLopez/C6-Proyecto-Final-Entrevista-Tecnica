package com.example.demo.service;

import java.util.List;

import static java.util.Collections.emptyList;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.EmptyReaderEventListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ICandidateDAO;
import com.example.demo.dto.Candidate;

@Service
public class CandidateServiceImpl implements ICandidateService, UserDetailsService{

	@Autowired
	ICandidateDAO iCandidateDAO;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	public CandidateServiceImpl(ICandidateDAO iCandidateDAO) {
		this.iCandidateDAO = iCandidateDAO;
	}
	
	@Override
	public List<Candidate> listarCandidates() {
		return iCandidateDAO.findAll();
	}

	@Override
	public Candidate guardarCandidate(Candidate candidates) {
		return iCandidateDAO.save(candidates);
	}

	@Override
	public Candidate candidatesXID(int id) {
		return iCandidateDAO.findById(id).get();
	}

	@Override
	public Candidate actualizarCandidates(Candidate candidates) {
		return iCandidateDAO.save(candidates);
	}

	@Override
	public void eliminarCandidates(int id) {
		iCandidateDAO.deleteById(id);
		
	}

	@Override
	public List<Candidate> findByName(String name) {
		return iCandidateDAO.findByName(name);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Candidate user = iCandidateDAO.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	public Candidate save(Candidate user) {
		Candidate newUser = new Candidate();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return iCandidateDAO.save(newUser);
	}

}

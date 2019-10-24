package com.springbootflyway;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/springbootflyway")
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;

	@RequestMapping(value = "/student", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	@GetMapping("/student/{id}")
	public Student getStudent(@PathVariable Integer id) {
		return studentRepository.findOne(id);
	}

	@PostMapping("/student")
	public Student createStudent(@RequestBody Student student, UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/student/{id}").buildAndExpand(student.getId()).toUri());
		return studentRepository.save(student);
	}

	@PutMapping("/student/{id}")
	public Student updateStudent(@PathVariable Integer id, @RequestBody Student student) {
		Student existingStudent = studentRepository.getOne(id);
		BeanUtils.copyProperties(student, existingStudent);
		return studentRepository.saveAndFlush(existingStudent);

	}

	@DeleteMapping("/student/{id}")
	public void deleteStudent(@PathVariable Integer id) {
		Student existingStudent = studentRepository.findOne(id);
		studentRepository.delete(existingStudent);
	}
}

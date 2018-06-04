package br.com.fatec.proximatrilha.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.fatec.proximatrilha.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}

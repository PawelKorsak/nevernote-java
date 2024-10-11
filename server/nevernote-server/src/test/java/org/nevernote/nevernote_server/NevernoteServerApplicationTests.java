package org.nevernote.nevernote_server;

import org.junit.jupiter.api.Test;
import org.nevernote.note_controller.NoteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NevernoteServerApplicationTests {

	@Autowired
	private NoteController noteController;

	@Test
	void contextLoads() {
	}

	@Test
	void noteTests(){
		assertThat(noteController).isNotNull();
	}

}

import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [notes, setNotes] = useState([]);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [editId, setEditId] = useState(null);

  // Funkcja logowania
  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const response = await axios.post('http://localhost:8080/api/login', {
        username,
        password,
      });
      if (response.status === 200) {
        setIsLoggedIn(true);
        fetchNotes(); // Pobierz notatki po zalogowaniu
      }
    } catch (error) {
      setError('Invalid credentials');
    }
  };

  // Pobieranie notatek
  const fetchNotes = async () => {
    try {
      const response = await axios.get('http://localhost:8080/notes');
      setNotes(response.data);
    } catch (error) {
      console.error("Błąd pobierania notatek:", error);
    }
  };

  // Dodawanie nowej notatki
  const addNote = async () => {
    try {
      await axios.post('http://localhost:8080/notes', { title, content });
      setTitle('');
      setContent('');
      fetchNotes();
    } catch (error) {
      console.error("Błąd tworzenia notatki:", error);
    }
  };

  // Aktualizacja notatki
  const updateNote = async () => {
    try {
      await axios.put(`http://localhost:8080/notes/${editId}`, { title, content });
      setTitle('');
      setContent('');
      setEditId(null);
      fetchNotes();
    } catch (error) {
      console.error("Błąd aktualizacji notatki:", error);
    }
  };

  // Usuwanie notatki
  const deleteNote = async (id) => {
    try {
      await axios.delete(`http://localhost:80800/notes/${id}`);
      fetchNotes();
    } catch (error) {
      console.error("Błąd usuwania notatki:", error);
    }
  };

  // Ustawianie edytowanej notatki
  const editNote = (note) => {
    setTitle(note.title);
    setContent(note.content);
    setEditId(note.id);
  };

  return (
      <div className="App">
        {isLoggedIn ? (
            <div>
              <h2>Witaj, {username}!</h2>
              <div>
                <input
                    type="text"
                    placeholder="Tytuł notatki"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                />
                <textarea
                    placeholder="Treść notatki"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                />
                <button onClick={editId ? updateNote : addNote}>
                  {editId ? 'Zaktualizuj notatkę' : 'Dodaj notatkę'}
                </button>
              </div>
              <div>
                <h3>Lista notatek</h3>
                {notes.map((note) => (
                    <div key={note.id}>
                      <h4>{note.title}</h4>
                      <p>{note.content}</p>
                      <button onClick={() => editNote(note)}>Edytuj</button>
                      <button onClick={() => deleteNote(note.id)}>Usuń</button>
                    </div>
                ))}
              </div>
            </div>
        ) : (
            <form onSubmit={handleLogin}>
              <h2>Login</h2>
              <div>
                <label>Username:</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
              </div>
              <div>
                <label>Password:</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
              </div>
              <button type="submit">Login</button>
              {error && <p style={{ color: 'red' }}>{error}</p>}
            </form>
        )}
      </div>
  );
}

export default App;

import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [user, setUser] = useState(null);
  const emptyNote ={
    id: '',
    title: '',
    description: '',
    ownerId: ''
  }
  const [note, setNote] = useState(emptyNote);
  const [error, setError] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [notes, setNotes] = useState([]);

  const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api'
  })

  axiosInstance.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('authToken');
        if (token){
          config.headers['Authorization'] =`Bearer ${token}`;
        }
        return config;
      }, (error) =>{
        console.log(error)
        return Promise.reject(error);
      }
  )


  // Funkcja logowania
  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const response = await axiosInstance.post('/login', {
        username,
        password,
      });
      if (response.status === 200) {
        setUser(response.data.user);
        localStorage.setItem('authToken', response.data.token);
        setIsLoggedIn(true);
        fetchNotes(); // Pobierz notatki po zalogowaniu
      }
    } catch (error) {
      setError('Invalid credentials');
      localStorage.removeItem('authToken');
    }
  };

  // Pobieranie notatek
  const fetchNotes = async () => {
    try {
      const response = await axiosInstance.get('/note');
      setNotes(response.data);
    } catch (error) {
      console.error("Błąd pobierania notatek:", error);
    }
  };

  // Dodawanie nowej notatki
  const addNote = async () => {
    try {
      const updatedNote = {
        ...note,
        ownerId: user.id
      }
      await axiosInstance.post('/note', updatedNote);
      setNote(emptyNote);
      await fetchNotes();
    } catch (error) {
      console.error("Błąd tworzenia notatki:", error);
    }
  };

  // Aktualizacja notatki
  const updateNote = async () => {
    try {
      await axiosInstance.put(`/note/${note.id}`, note);
      setNote(emptyNote);
      await fetchNotes();
    } catch (error) {
      console.error("Błąd aktualizacji notatki:", error);
    }
  };

  // Usuwanie notatki
  const deleteNote = async (id) => {
    try {
      await axiosInstance.delete(`/note/${id}`);
      await fetchNotes();
    } catch (error) {
      console.error("Błąd usuwania notatki:", error);
    }
  };

  // Ustawianie edytowanej notatki
  const editNote = (note) => {
    setNote(note);
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
                    value={note.title}
                    onChange={(e) => setNote({...note, title: e.target.value})}
                />
                <textarea
                    placeholder="Treść notatki"
                    value={note.description}
                    onChange={(e) => setNote({...note, description: e.target.value})}
                />
                <button onClick={note.id ? updateNote : addNote}>
                  {note.id ? 'Zaktualizuj notatkę' : 'Dodaj notatkę'}
                </button>
              </div>
              <div>
                <h3>Lista notatek</h3>
                {notes.map((note) => (
                    <div key={note.id}>
                      <h4>{note.title}</h4>
                      <p>{note.description}</p>
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

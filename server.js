const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
app.use(bodyParser.json());
app.use(cors());

let users = [{ id: 1, name: 'John', email: 'john@test.com' }];
let nextUserId = 2;

// Health check
app.get('/api/health', (req, res) => {
  res.status(200).json({ status: 'OK' });
});

// Get all users
app.get('/api/users', (req, res) => {
  res.status(200).json(users);
});

// Get user by ID
app.get('/api/users/:id', (req, res) => {
  const user = users.find(u => u.id === parseInt(req.params.id));
  if (!user) return res.status(404).json({ error: 'Not found' });
  res.status(200).json(user);
});

// Create user
app.post('/api/users', (req, res) => {
  const { name, email } = req.body;
  if (!name || !email) {
    return res.status(400).json({ error: 'Name and email required' });
  }
  const newUser = { id: nextUserId++, name, email };
  users.push(newUser);
  res.status(201).json(newUser);
});

// Update user
app.put('/api/users/:id', (req, res) => {
  const user = users.find(u => u.id === parseInt(req.params.id));
  if (!user) return res.status(404).json({ error: 'Not found' });
  if (req.body.name) user.name = req.body.name;
  if (req.body.email) user.email = req.body.email;
  res.status(200).json(user);
});

// Delete user
app.delete('/api/users/:id', (req, res) => {
  const index = users.findIndex(u => u.id === parseInt(req.params.id));
  if (index === -1) return res.status(404).json({ error: 'Not found' });
  users.splice(index, 1);
  res.status(204).send();
});

const PORT = 3000;
app.listen(PORT, () => {
  console.log(`✅ Server running on http://localhost:${PORT}`);
});

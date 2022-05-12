import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ReactDOM from 'react-dom/client';
import './index.css';
import Animate from './Animate';

export default function Websole() {
  return (
    <BrowserRouter>
      <Routes>
          <Route index element={<Animate />} />
      </Routes>
    </BrowserRouter>
  );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<Websole />);


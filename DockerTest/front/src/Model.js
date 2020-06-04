import React from 'react'
import { Dropdown } from 'semantic-ui-react'

const options = [
  { key: 'bmw3', text: '3-Series', value: 'bmw-3' },
  { key: 'bmw5', text: '5-Series', value: 'bmw-5' },
  { key: 'bmw7', text: '7-Series', value: 'bmw-7' },
  { key: 'audi3', text: 'A3', value: 'audi-a3' },
  { key: 'audi4', text: 'A4', value: 'audi-a4' },
  { key: 'audi6', text: 'A6', value: 'audi-a6' },
]

const Model = () => (
  <Dropdown
    placeholder='Model'
    fluid 
    search
    selection
    options={options}
  />
)

export default Model
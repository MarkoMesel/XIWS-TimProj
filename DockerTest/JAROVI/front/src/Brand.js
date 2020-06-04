import React from 'react'
import { Dropdown } from 'semantic-ui-react'

const options = []

const Brand = () => (
  <Dropdown
    placeholder='Brand'
    fluid
    search
    selection
    options={options}
  />
)

export default Brand
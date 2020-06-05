
import React from 'react';

export const FormErrors = ({formErrors}) =>
  <div className='formErrors'>
    {Object.keys(formErrors).map((fieldName, i) => {
      if(formErrors[fieldName].length > 0){
        return (
          <span key={i} style={{ color: 'red' }} className="form-label">{fieldName} {formErrors[fieldName]}</span>
        )        
      } else {
        return '';
      }
    })}
  </div>
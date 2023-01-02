import uuid from 'react-uuid';
import React from 'react';

export const iconTemplate = (_option: { link: string }) => {
  return (
    <div key={uuid()}>
      <span className="me-2">
        <img src={_option.link} alt={_option.link} width="32px" height="32px"></img>
      </span>
      {_option.link}
    </div>
  );
};

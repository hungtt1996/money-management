import React from 'react';
import { Control, Controller, FieldErrors } from 'react-hook-form';
import Select from 'react-select';

interface IMmSelectProps {
  control: Control;
  formControlName: string;
  options: Array<any>;
  errors: FieldErrors;
  isMulti?: boolean;
  isClearable?: boolean;
  onChangeValue?: (...args) => void;
  placeholder: string;
  optionLabel: string;
  optionValue: string;
  customLabel?: (...args) => void;
}

const MmSelect = (props: IMmSelectProps) => {
  const {
    formControlName,
    errors,
    control,
    isClearable,
    isMulti,
    placeholder,
    options,
    optionLabel,
    optionValue,
    onChangeValue,
    customLabel,
  } = props;

  const privateHandleOnchange = (val: any, field: any) => {
    if (onChangeValue) {
      onChangeValue(val);
    }
    if (isMulti) {
      const selectedOptions = val.reduce((acc: any, next: any) => acc.concat(next[optionValue]), []);
      return field.onChange(selectedOptions);
    }
    return field.onChange(val ? val[optionValue] : '');
  };

  const handleOnchange = (field: any) => {
    return isMulti
      ? options.filter(c => (field.value ? field.value.includes(c[optionValue]) : []))
      : options.find(c => c[optionValue] === field.value) || '';
  };

  return (
    <>
      <Controller
        name={formControlName}
        control={control}
        render={({ field }) => (
          <Select
            {...field}
            isMulti={isMulti}
            isClearable={isClearable}
            placeholder={placeholder}
            options={options}
            value={handleOnchange(field)}
            getOptionLabel={option => (customLabel ? customLabel(option) : option[optionLabel])}
            getOptionValue={option => option[optionValue]}
            onChange={val => privateHandleOnchange(val, field)}
            isSearchable
          />
        )}
      />
      <div className="error-msg">{errors[formControlName]?.message}</div>
    </>
  );
};

export default MmSelect;

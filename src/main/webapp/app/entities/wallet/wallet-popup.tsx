import React, { useEffect } from 'react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Button, Col, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'reactstrap';
import MmSelect from 'app/shared/itegration/mm-select';
import { ICON_LINKS_ARRAY } from 'app/shared/util/constant';
import * as Yup from 'yup';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { createEntity, updateEntity } from 'app/entities/wallet/wallet.reducer';
import { IWallet } from 'app/shared/model/wallet.model';
import { iconTemplate } from 'app/shared/itegration/mm-template';

interface IWalletPopupProps {
  visible: boolean;
  onHidePopup: (...args) => void;
}

const WalletPopup = (props: IWalletPopupProps) => {
  const { visible, onHidePopup } = props;
  const dispatch = useAppDispatch();

  const walletEntity: IWallet = useAppSelector(state => state.wallet.entity);

  const validationSchema = Yup.object().shape({
    code: Yup.string().required('Field is required'),
    nameVi: Yup.string().required('Field is required'),
    nameEn: Yup.string().required('Field is required'),
    icon: Yup.string().required('Field is required'),
    balance: Yup.string().required('Field is required'),
    ccy: Yup.string().required('Field is required'),
  });

  const {
    handleSubmit,
    register,
    formState: { errors },
    control,
    setValue,
  } = useForm({
    mode: 'all',
    resolver: yupResolver(validationSchema),
  });

  useEffect(() => {
    if (walletEntity) {
      setValue('code', walletEntity.code);
      setValue('nameVi', walletEntity.nameVi);
      setValue('nameEn', walletEntity.nameEn);
      setValue('icon', walletEntity.icon);
      setValue('balance', walletEntity.balance);
      setValue('ccy', walletEntity.ccy);
    }
  }, [walletEntity]);

  const handleHidePopup = () => {
    onHidePopup();
  };

  const onSubmit = (_data: any) => {
    const entity = {
      ...walletEntity,
      ..._data,
    };
    // Tạo mới
    if (walletEntity) {
      dispatch(updateEntity(entity));
    } else {
      // Cập nhật
      dispatch(createEntity(entity));
    }
  };

  return (
    <Modal isOpen={visible} toggle={handleHidePopup} backdrop="static" id="wallet-update" autoFocus={false}>
      <form onSubmit={handleSubmit(onSubmit)}>
        <ModalHeader id="login-title" data-cy="loginTitle" toggle={handleHidePopup}>
          <div>{walletEntity ? 'Chỉnh sửa Ví' : 'Thêm mới Ví'}</div>
        </ModalHeader>
        <ModalBody>
          <Row>
            <Col md="12">
              <div className="form-group">
                <label className="required block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Mã Ví</label>
                <input id="code" type="text" placeholder="Nhập Mã Ví" {...register('code')} autoComplete="off" className="form-control" />
                <div className="error-msg">{errors.code?.message}</div>
              </div>

              <div className="form-group">
                <label className="required block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Name Vi</label>
                <input
                  id="nameVi"
                  type="text"
                  placeholder="Nhập Name Vi"
                  {...register('nameVi')}
                  autoComplete="off"
                  className="form-control"
                />
                <div className="error-msg">{errors.nameVi?.message}</div>
              </div>

              <div className="form-group">
                <label className="required block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Name En</label>
                <input
                  id="nameEn"
                  type="text"
                  placeholder="Nhập Name En"
                  {...register('nameEn')}
                  autoComplete="off"
                  className="form-control"
                />
                <div className="error-msg">{errors.nameEn?.message}</div>
              </div>

              <div className="form-group">
                <label className="required block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Icon</label>
                <MmSelect
                  control={control}
                  formControlName="icon"
                  options={ICON_LINKS_ARRAY}
                  errors={errors}
                  placeholder={'Lựa chọn'}
                  optionLabel="link"
                  optionValue="link"
                  customLabel={iconTemplate}
                />
              </div>

              <div className="form-group">
                <label className="required block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Số dư</label>
                <input
                  id="balance"
                  type="text"
                  placeholder="Nhập Số dư"
                  {...register('balance')}
                  autoComplete="off"
                  className="form-control"
                />
                <div className="error-msg">{errors.balance?.message}</div>
              </div>

              <div className="form-group">
                <label className="required block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Loại tiền</label>
                <input id="ccy" type="text" placeholder="Nhập Loại tiền" {...register('ccy')} autoComplete="off" className="form-control" />
                <div className="error-msg">{errors.ccy?.message}</div>
              </div>
            </Col>
          </Row>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={() => handleHidePopup()} tabIndex={1}>
            Hủy
          </Button>{' '}
          <Button color="primary" type="submit" data-cy="submit">
            Thực hiện
          </Button>
        </ModalFooter>
      </form>
    </Modal>
  );
};

export default WalletPopup;

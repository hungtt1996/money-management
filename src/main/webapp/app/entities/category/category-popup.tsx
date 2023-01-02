import React, { useEffect } from 'react';
import { Button, Col, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'reactstrap';
import { useForm } from 'react-hook-form';
import * as Yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { ICON_LINKS_ARRAY } from 'app/shared/util/constant';
import MmSelect from 'app/shared/itegration/mm-select';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { createEntity, getEntities as getCategories, updateEntity } from 'app/entities/category/category.reducer';
import { getEntities } from 'app/entities/group/group.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { iconTemplate } from 'app/shared/itegration/mm-template';

interface ICategoryPopup {
  visible: boolean;
  onHidePopup: () => void;
}

const CategoryPopup = (props: ICategoryPopup) => {
  const { visible, onHidePopup } = props;
  const dispatch = useAppDispatch();

  const categoryEntity: ICategory = useAppSelector(state => state.category.entity);
  const groupList = useAppSelector(state => state.group.entities);
  const categoryList = useAppSelector(state => state.category.entities);

  const validationSchema = Yup.object().shape({
    code: Yup.string().required('Field is required'),
    nameVi: Yup.string().required('Field is required'),
    nameEn: Yup.string().required('Field is required'),
    icon: Yup.string().required('Field is required'),
    groupId: Yup.string().required('Field is required'),
    parentId: Yup.string().nullable(),
    level: Yup.string().required('Field is required'),
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
    if (groupList.length < 1) {
      dispatch(getEntities({}));
    }
    if (categoryList.length < 1) {
      dispatch(getCategories({}));
    }
  }, []);

  useEffect(() => {
    if (categoryEntity) {
      setValue('code', categoryEntity.code);
      setValue('nameVi', categoryEntity.nameVi);
      setValue('nameEn', categoryEntity.nameEn);
      setValue('icon', categoryEntity.icon);
      setValue('groupId', categoryEntity.group.id);
      setValue('parentId', categoryEntity.parentId);
      setValue('level', categoryEntity.level);
    }
  }, [categoryEntity]);

  const handleHidePopup = () => {
    onHidePopup();
  };

  const onSubmit = (_data: any) => {
    const entity = {
      ...categoryEntity,
      ..._data,
    };
    // Tạo mới
    if (categoryEntity) {
      dispatch(updateEntity(entity));
    } else {
      // Cập nhật
      dispatch(createEntity(entity));
    }
  };

  return (
    <Modal isOpen={visible} toggle={handleHidePopup} backdrop="static" id="category-update" autoFocus={false}>
      <form onSubmit={handleSubmit(onSubmit)}>
        <ModalHeader id="login-title" data-cy="loginTitle" toggle={handleHidePopup}>
          <div>{categoryEntity ? 'Chỉnh sửa Category' : 'Thêm mới Category'}</div>
        </ModalHeader>
        <ModalBody>
          <Row>
            <Col md="12">
              <div className="form-group">
                <label className="required block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Mã Category</label>
                <input
                  id="code"
                  type="text"
                  placeholder="Nhập Mã Category"
                  {...register('code')}
                  autoComplete="off"
                  className="form-control"
                />
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
                <label className="required block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Group</label>
                <MmSelect
                  control={control}
                  formControlName="groupId"
                  options={groupList}
                  errors={errors}
                  placeholder={'Lựa chọn'}
                  optionLabel="nameVi"
                  optionValue="id"
                />
              </div>

              <div className="form-group">
                <label className="block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Parent</label>
                <MmSelect
                  control={control}
                  formControlName="parentId"
                  options={categoryList}
                  errors={errors}
                  placeholder={'Lựa chọn'}
                  optionLabel="nameVi"
                  optionValue="id"
                />
              </div>

              <div className="form-group">
                <label className="required block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">Level</label>
                <input id="level" type="text" placeholder="Nhập Level" {...register('level')} autoComplete="off" className="form-control" />
                <div className="error-msg">{errors.level?.message}</div>
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

export default CategoryPopup;

import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICategory } from 'app/shared/model/category.model';
import { getEntity, updateEntity, createEntity, reset, createCategory } from './category.reducer';
import { languages, locales } from 'app/config/translation';
import { ICON_LINKS } from 'app/shared/util/constant';
import { getEntities } from 'app/entities/group/group.reducer';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';

export const CategoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const categoryEntity = useAppSelector(state => state.category.entity);
  const loading = useAppSelector(state => state.category.loading);
  const updating = useAppSelector(state => state.category.updating);
  const updateSuccess = useAppSelector(state => state.category.updateSuccess);
  const groupList = useAppSelector(state => state.group.entities);
  const categoryList = useAppSelector(state => state.category.entities);

  const handleClose = () => {
    navigate('/category');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(createCategory());
    } else {
      dispatch(getEntity(id));
    }
    if (groupList.length < 1) {
      dispatch(getEntities({}));
    }
    if (categoryList.length < 1) {
      dispatch(getCategories({}));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...categoryEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...categoryEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="moneyManagementApp.category.home.createOrEditLabel" data-cy="CategoryCreateUpdateHeading">
            <Translate contentKey="moneyManagementApp.category.home.createOrEditLabel">Create or edit a Category</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="category-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('moneyManagementApp.category.code')}
                id="category-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('moneyManagementApp.category.nameVi')}
                id="category-nameVi"
                name="nameVi"
                data-cy="nameVi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('moneyManagementApp.category.nameEn')}
                id="category-nameEn"
                name="nameEn"
                data-cy="nameEn"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="category-icon"
                type="select"
                name="icon"
                data-cy="icon"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
                label={translate('moneyManagementApp.category.icon')}
              >
                {ICON_LINKS.sort().map(link => (
                  <option value={link} key={link}>
                    <span>
                      {link} <img src={link} alt={link} />
                    </span>
                  </option>
                ))}
              </ValidatedField>
              {/*<ValidatedField
                label={translate('moneyManagementApp.category.icon')}
                id="category-icon"
                name="icon"
                data-cy="icon"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />*/}
              <ValidatedField
                label={translate('moneyManagementApp.category.groupId')}
                id="category-groupId"
                name="groupId"
                data-cy="groupId"
                type="select"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              >
                {groupList.map(g => (
                  <option value={g.code} key={g.code}>
                    {g.nameVi}
                  </option>
                ))}
              </ValidatedField>
              {/*<ValidatedField
                label={translate('moneyManagementApp.category.groupId')}
                id="category-groupId"
                name="groupId"
                data-cy="groupId"
                type="text"
                validate={{
                  required: {value: true, message: translate('entity.validation.required')},
                }}
              />*/}
              <ValidatedField
                label={translate('moneyManagementApp.category.parentId')}
                id="category-parentId"
                name="parentId"
                data-cy="parentId"
                type="select"
              >
                {[{ id: null, nameVi: 'Nhóm Cha', level: 0 }, ...categoryList]
                  .filter(c => c.level === 0)
                  .map(c => (
                    <option value={c.id} key={c.id}>
                      {c.nameVi}
                    </option>
                  ))}
              </ValidatedField>
              {/*<ValidatedField
                label={translate('moneyManagementApp.category.parentId')}
                id="category-parentId"
                name="parentId"
                data-cy="parentId"
                type="text"
              />*/}
              <ValidatedField
                label={translate('moneyManagementApp.category.level')}
                id="category-level"
                name="level"
                data-cy="level"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/category" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CategoryUpdate;

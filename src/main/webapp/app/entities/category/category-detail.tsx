import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './category.reducer';

export const CategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const categoryEntity = useAppSelector(state => state.category.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoryDetailsHeading">
          <Translate contentKey="moneyManagementApp.category.detail.title">Category</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="moneyManagementApp.category.code">Code</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.code}</dd>
          <dt>
            <span id="nameVi">
              <Translate contentKey="moneyManagementApp.category.nameVi">Name Vi</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.nameVi}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="moneyManagementApp.category.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.nameEn}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="moneyManagementApp.category.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.icon}</dd>
          <dt>
            <span id="groupId">
              <Translate contentKey="moneyManagementApp.category.groupId">Group Id</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.groupId}</dd>
          <dt>
            <span id="parentId">
              <Translate contentKey="moneyManagementApp.category.parentId">Parent Id</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.parentId}</dd>
          <dt>
            <span id="level">
              <Translate contentKey="moneyManagementApp.category.level">Level</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.level}</dd>
        </dl>
        <Button tag={Link} to="/category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/category/${categoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoryDetail;

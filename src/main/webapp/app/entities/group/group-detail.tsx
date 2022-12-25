import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './group.reducer';

export const GroupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const groupEntity = useAppSelector(state => state.group.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="groupDetailsHeading">
          <Translate contentKey="moneyManagementApp.group.detail.title">Group</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{groupEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="moneyManagementApp.group.code">Code</Translate>
            </span>
          </dt>
          <dd>{groupEntity.code}</dd>
          <dt>
            <span id="nameVi">
              <Translate contentKey="moneyManagementApp.group.nameVi">Name Vi</Translate>
            </span>
          </dt>
          <dd>{groupEntity.nameVi}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="moneyManagementApp.group.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{groupEntity.nameEn}</dd>
        </dl>
        <Button tag={Link} to="/group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/group/${groupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GroupDetail;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wallet.reducer';

export const WalletDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const walletEntity = useAppSelector(state => state.wallet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="walletDetailsHeading">
          <Translate contentKey="moneyManagementApp.wallet.detail.title">Wallet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{walletEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="moneyManagementApp.wallet.code">Code</Translate>
            </span>
          </dt>
          <dd>{walletEntity.code}</dd>
          <dt>
            <span id="nameVi">
              <Translate contentKey="moneyManagementApp.wallet.nameVi">Name Vi</Translate>
            </span>
          </dt>
          <dd>{walletEntity.nameVi}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="moneyManagementApp.wallet.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{walletEntity.nameEn}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="moneyManagementApp.wallet.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{walletEntity.balance}</dd>
          <dt>
            <span id="ccy">
              <Translate contentKey="moneyManagementApp.wallet.ccy">Ccy</Translate>
            </span>
          </dt>
          <dd>{walletEntity.ccy}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="moneyManagementApp.wallet.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{walletEntity.icon}</dd>
        </dl>
        <Button tag={Link} to="/wallet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wallet/${walletEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WalletDetail;

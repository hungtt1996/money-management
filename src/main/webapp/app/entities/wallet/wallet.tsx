import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWallet } from 'app/shared/model/wallet.model';
import { getEntities } from './wallet.reducer';

export const Wallet = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const walletList = useAppSelector(state => state.wallet.entities);
  const loading = useAppSelector(state => state.wallet.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="wallet-heading" data-cy="WalletHeading">
        <Translate contentKey="moneyManagementApp.wallet.home.title">Wallets</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="moneyManagementApp.wallet.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/wallet/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="moneyManagementApp.wallet.home.createLabel">Create new Wallet</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {walletList && walletList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="moneyManagementApp.wallet.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.wallet.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.wallet.nameVi">Name Vi</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.wallet.nameEn">Name En</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.wallet.balance">Balance</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.wallet.ccy">Ccy</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.wallet.icon">Icon</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {walletList.map((wallet, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/wallet/${wallet.id}`} color="link" size="sm">
                      {wallet.id}
                    </Button>
                  </td>
                  <td>{wallet.code}</td>
                  <td>{wallet.nameVi}</td>
                  <td>{wallet.nameEn}</td>
                  <td>{wallet.balance}</td>
                  <td>{wallet.ccy}</td>
                  <td>{wallet.icon}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/wallet/${wallet.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/wallet/${wallet.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/wallet/${wallet.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="moneyManagementApp.wallet.home.notFound">No Wallets found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Wallet;

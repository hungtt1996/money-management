import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { createWallet, getEntities, getEntity } from './wallet.reducer';
import WalletPopup from 'app/entities/wallet/wallet-popup';
import { IWallet } from 'app/shared/model/wallet.model';

export const Wallet = () => {
  const dispatch = useAppDispatch();

  const walletList: IWallet[] = useAppSelector(state => state.wallet.entities);
  const loading = useAppSelector(state => state.wallet.loading);

  const [visiblePopup, setVisiblePopup] = useState(false);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const handleHidePopup = (...args) => {
    setVisiblePopup(false);
  };

  const handleCreateNewWallet = () => {
    dispatch(createWallet()).then(() => {
      setVisiblePopup(true);
    });
  };

  const editWallet = (_wallet: IWallet) => {
    dispatch(getEntity(_wallet.id)).then(() => {
      setVisiblePopup(true);
    });
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
          <Button className="me-2" color="primary" onClick={handleCreateNewWallet} disabled={loading}>
            <FontAwesomeIcon icon="plus" spin={loading} />{' '}
            <Translate contentKey="moneyManagementApp.wallet.home.createLabel">Create new Category</Translate>
          </Button>
        </div>
      </h2>
      <div className="table-responsive">
        {walletList && walletList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                {/* <th>
                                <Translate contentKey="moneyManagementApp.wallet.id">ID</Translate>
                            </th>*/}
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
                <th />
              </tr>
            </thead>
            <tbody>
              {walletList.map((wallet, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  {/* <td>
                                    <Button tag={Link} to={`/wallet/${wallet.id}`} color="link" size="sm">
                                        {wallet.id}
                                    </Button>
                                </td>*/}
                  <td>{wallet.code}</td>
                  <td>
                    <span>
                      <img src={wallet.icon} alt="icon" width="48px" height="48px" />
                    </span>{' '}
                    {wallet.nameVi}
                  </td>
                  <td>{wallet.nameEn}</td>
                  <td>{wallet.balance.toLocaleString()}</td>
                  <td>{wallet.ccy}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/wallet/${wallet.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button color="primary" size="sm" data-cy="entityEditButton" onClick={() => editWallet(wallet)}>
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

      {visiblePopup && <WalletPopup visible={visiblePopup} onHidePopup={handleHidePopup} />}
    </div>
  );
};

export default Wallet;
